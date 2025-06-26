/*
 * Variant - A digital comic book reading application for the iPad and Android tablets.
 * Copyright (C) 2025, The ComiXed Project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses>
 */

package org.comixedproject.variant.viewmodel

import com.oldguy.common.io.File
import com.oldguy.common.io.FileMode
import com.oldguy.common.io.RawFile
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.MutableStateFlow
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.coroutineScope
import com.rickclephas.kmp.observableviewmodel.launch
import io.github.irgaly.kfswatch.KfsDirectoryWatcher
import io.github.irgaly.kfswatch.KfsDirectoryWatcherEvent
import io.github.irgaly.kfswatch.KfsLogger
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.comixedproject.variant.database.repository.DirectoryRepository
import org.comixedproject.variant.database.repository.ServerRepository
import org.comixedproject.variant.model.Server
import org.comixedproject.variant.model.library.ComicBook
import org.comixedproject.variant.model.library.DirectoryEntry
import org.comixedproject.variant.model.state.DownloadingState
import org.comixedproject.variant.platform.Log
import org.comixedproject.variant.reader.ReaderAPI

private const val TAG = "VariantViewModel"

open class VariantViewModel(
    val serverRepository: ServerRepository,
    val directoryRepository: DirectoryRepository
) : ViewModel() {
    private var _libraryDirectory = ""
    private var libraryWatcher: KfsDirectoryWatcher? = null

    fun setLibraryDirectory(directory: String) {
        Log.debug(TAG, "_libraryDirectory=${directory}")
        _libraryDirectory = directory
        viewModelScope.coroutineScope.launch {

            if (!File(_libraryDirectory).exists) {
                Log.info(TAG, "Creating library directory: ${_libraryDirectory}")
                File(_libraryDirectory).makeDirectory()
            }

            libraryWatcher?.let { watcher ->
                Log.debug(TAG, "Stopping current library monitor")
                watcher.removeAll()
                watcher.close()
            }

            loadLibraryContents()

            Log.debug(TAG, "Preparing to monitor library: ${_libraryDirectory}")
            libraryWatcher = KfsDirectoryWatcher(
                scope = viewModelScope.coroutineScope,
                logger = object : KfsLogger {
                    override fun debug(message: String) {
                        Log.debug(TAG, message)
                    }

                    override fun error(message: String) {
                        Log.error(TAG, message)
                    }
                })
            libraryWatcher?.add(_libraryDirectory)
            libraryWatcher?.onEventFlow?.collect { event: KfsDirectoryWatcherEvent ->
                viewModelScope.coroutineScope.launch {
                    Log.debug(TAG, "Received file watcher event: ${event}")
                    loadLibraryContents()
                }
            }
        }
    }

    private val _serverList =
        MutableStateFlow<List<Server>>(viewModelScope, serverRepository.servers)

    @NativeCoroutinesState
    val serverList: StateFlow<List<Server>> = _serverList.asStateFlow()

    private val _editing = MutableStateFlow<Boolean>(viewModelScope, false)

    @NativeCoroutinesState
    val editing: StateFlow<Boolean> = _editing.asStateFlow()

    private val _editingServer = MutableStateFlow<Server?>(viewModelScope, null)

    @NativeCoroutinesState
    val editingServer: StateFlow<Server?> = _editingServer.asStateFlow()

    private val _browsing = MutableStateFlow<Boolean>(viewModelScope, false)

    @NativeCoroutinesState
    val browsing: StateFlow<Boolean> = _browsing.asStateFlow()

    private val _browsingServer = MutableStateFlow<Server?>(viewModelScope, null)

    @NativeCoroutinesState
    val browsingServer: StateFlow<Server?> = _browsingServer.asStateFlow()

    private val _currentPath = MutableStateFlow<String>(viewModelScope, "")

    @NativeCoroutinesState
    val currentPath: StateFlow<String> = _currentPath.asStateFlow()

    private val _title = MutableStateFlow<String>(viewModelScope, "")

    @NativeCoroutinesState
    val title: StateFlow<String> = _title.asStateFlow()

    private val _parentPath = MutableStateFlow<String>(viewModelScope, "")

    @NativeCoroutinesState
    val parentPath: StateFlow<String> = _parentPath.asStateFlow()

    private val _directoryContents =
        MutableStateFlow<List<DirectoryEntry>>(viewModelScope, emptyList())

    @NativeCoroutinesState
    val directoryContents: StateFlow<List<DirectoryEntry>> = _directoryContents.asStateFlow()

    private val _loading = MutableStateFlow<Boolean>(viewModelScope, false)

    @NativeCoroutinesState
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _downloadingState =
        MutableStateFlow<MutableList<DownloadingState>>(viewModelScope, mutableListOf())

    @NativeCoroutinesState
    val downloadingState: StateFlow<MutableList<DownloadingState>> = _downloadingState.asStateFlow()

    private val _comicBookList =
        MutableStateFlow<List<ComicBook>>(viewModelScope, listOf())

    @NativeCoroutinesState
    val comicBookList: StateFlow<List<ComicBook>> = _comicBookList.asStateFlow()

    fun editServer(server: Server?) {
        server?.let {
            Log.debug(TAG, "Editing server: ${it.name}")
        }
        viewModelScope.launch {
            _editing.emit(server != null)
            _editingServer.emit(server)
        }
    }

    suspend fun saveServer(server: Server) {
        Log.debug(TAG, "Saving server: ${server.name} ${server.url}")
        serverRepository.save(server)
        doReloadServers()
    }

    private suspend fun doReloadServers() {
        Log.debug(TAG, "Loading server list")
        val servers = serverRepository.servers
        _serverList.emit(servers)
    }

    suspend fun loadDirectory(server: Server, path: String, reload: Boolean) {
        var contents = directoryRepository.loadDirectoryContents(server, path)
        if (contents.isEmpty() || reload) {
            _loading.emit(true)

            try {
                val url = URLBuilder(server.url).takeFrom(path).build()
                Log.debug(
                    TAG,
                    "Loading directory contents: server=${server.name} url=${url} reload=${reload}"
                )
                contents = ReaderAPI.loadDirectory(server, url).contents.map {
                    DirectoryEntry(
                        id = null,
                        directoryId = it.directoryId,
                        serverId = server.serverId!!,
                        title = it.title,
                        path = it.path,
                        parent = path,
                        filename = it.filename,
                        isDirectory = it.directory,
                        coverUrl = it.coverUrl
                    )
                }
                directoryRepository.saveDirectoryContents(server, path, contents)
            } catch (error: Exception) {
                Log.error(TAG, "Failed to load directory: ${error.message}")
                error.printStackTrace()
            }
        }

        val directory = directoryRepository.findDirectory(path)

        _browsingServer.emit(server)
        _currentPath.emit(path)
        _title.emit(directory?.title ?: "")
        _parentPath.emit(directory?.parent ?: "")
        _directoryContents.emit(contents)
        _browsing.emit(true)
        _loading.emit(true)
    }

    suspend fun downloadFile(server: Server, path: String, filename: String) {
        Log.debug(TAG, "Starting download: ${server.name} path=${path}")

        val downloadingState = DownloadingState(server, path, 0, 0)
        val state = _downloadingState.value

        state.add(downloadingState)
        _downloadingState.emit(state)

        try {
            val url = URLBuilder(server.url).takeFrom(path).build()
            val outputFile = File(_libraryDirectory, filename)
            Log.debug(TAG, "Writing comic to local file: ${outputFile.fullPath}")

            val output = RawFile(outputFile, FileMode.Write)

            ReaderAPI.downloadComic(server, url, output, onProgress = { received, total ->
                viewModelScope.launch(Dispatchers.Main) {
                    val downloadingState = DownloadingState(server, path, received, total)
                    val state =
                        _downloadingState.value.filter { !(it.server.serverId == server.serverId && it.path == path) }
                            .toMutableList()
                    state.add(downloadingState)
                    Log.debug(
                        TAG,
                        "Download state update: ${downloadingState.path}=${downloadingState.received}/${downloadingState.total}"
                    )
                    _downloadingState.emit(state)
                }
            })
            Log.debug(TAG, "Preparing to close the output file")
            output.close()
            Log.debug(TAG, "Download complete")
        } catch (error: Exception) {
            Log.error(TAG, "Failed to download ${path}: ${error.message}")
            error.printStackTrace()
        }

        val finalState =
            _downloadingState.value.filter { !(it.server.serverId == server.serverId && it.path == path) }
                .toMutableList()
        _downloadingState.emit(finalState)
    }

    suspend fun stopBrowsing() {
        Log.debug(TAG, "Clearing the server browsing state")
        _browsing.emit(false)
    }

    private suspend fun loadLibraryContents() {
        Log.debug(TAG, "Loading library contents: ${_libraryDirectory}")

        val path = File(_libraryDirectory)
        val contents =
            path.directoryList()
                .map { File(it) }
                .filter { !it.isDirectory }
                .filter { it.extension.equals("cbz") || it.extension.equals("cbr") }
                .map { entry ->
                    Log.debug(TAG, "Found file: ${entry.path}")
                    ComicBook(
                        entry.fullPath,
                        entry.name,
                        entry.size,
                        entry.lastModifiedEpoch
                    )
                }.toList()
        _comicBookList.tryEmit(contents)
    }
}
