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
import com.russhwolf.settings.Settings
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
import org.comixedproject.variant.model.library.ComicBook
import org.comixedproject.variant.model.library.DirectoryEntry
import org.comixedproject.variant.model.state.DownloadingState
import org.comixedproject.variant.platform.Log
import org.comixedproject.variant.reader.READER_ROOT
import org.comixedproject.variant.reader.ReaderAPI

private const val TAG = "VariantViewModel"

private const val ADDRESS_SETTING = "server.address"
private const val USERNAME_SETTING = "server.username"
private const val PASSWORD_SETTING = "server.password"

open class VariantViewModel(
    val settings: Settings,
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

    var address: String
        get() = settings.getString(ADDRESS_SETTING, "")
        set(value) = settings.putString(ADDRESS_SETTING, value)

    var username: String
        get() = settings.getString(USERNAME_SETTING, "")
        set(value) = settings.putString(USERNAME_SETTING, value)

    var password: String
        get() = settings.getString(PASSWORD_SETTING, "")
        set(value) = settings.putString(PASSWORD_SETTING, value)

    private val _browsingState = MutableStateFlow<BrowsingState>(
        viewModelScope, BrowsingState(
            READER_ROOT,
            "", "", emptyList<DirectoryEntry>(), emptyList<DownloadingState>()
        )
    )

    @NativeCoroutinesState
    val browsingState: StateFlow<BrowsingState> = _browsingState.asStateFlow()

    private val _loading = MutableStateFlow<Boolean>(viewModelScope, false)

    @NativeCoroutinesState
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _comicBookList =
        MutableStateFlow<List<ComicBook>>(viewModelScope, listOf())

    @NativeCoroutinesState
    val comicBookList: StateFlow<List<ComicBook>> = _comicBookList.asStateFlow()

    fun loadDirectory(path: String, reload: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            Log.debug(TAG, "Loading directory: ${path}")
            var contents = directoryRepository.loadDirectoryContents(path)
            if (contents.isEmpty() || reload) {
                _loading.emit(true)

                try {
                    val url = URLBuilder(address).takeFrom(path)
                        .build()
                    Log.debug(
                        TAG,
                        "Loading directory contents: url=${url} reload=${reload}"
                    )
                    contents = ReaderAPI.loadDirectory(
                        url,
                        username,
                        password
                    ).contents.map {
                        DirectoryEntry(
                            id = null,
                            directoryId = it.directoryId,
                            title = it.title,
                            path = it.path,
                            parent = path,
                            filename = it.filename,
                            fileSize = it.fileSize,
                            isDirectory = it.directory,
                            coverUrl = it.coverUrl
                        )
                    }
                    directoryRepository.saveDirectoryContents(path, contents)
                } catch (error: Exception) {
                    Log.error(TAG, "Failed to load directory: ${error.message}")
                    error.printStackTrace()
                }
            }

            val directory = directoryRepository.findDirectory(path)
            Log.debug(TAG, "Updating browsing state: currentPath=${path}")
            _browsingState.emit(
                BrowsingState(
                    path,
                    directory?.parent ?: "",
                    directory?.title ?: "",
                    contents,
                    _browsingState.value.downloadingState
                )
            )
            _loading.emit(false)
        }
    }

    fun downloadFile(path: String, filename: String) {
        Log.debug(TAG, "Starting download: path=${path}")
        val address = this.address
        val username = this.username
        val password = this.password

        viewModelScope.launch(Dispatchers.Main) {
            val downloadingState = DownloadingState(path, 0, 0)
            val state = mutableListOf<DownloadingState>()
            state.addAll(_browsingState.value.downloadingState)
            state.add(downloadingState)
            doUpdateDownloadingState(state)

            try {
                val url = URLBuilder(address).takeFrom(path).build()
                val outputFile = File("${_libraryDirectory}/${filename}")
                Log.debug(TAG, "Writing comic to local file: ${outputFile.fullPath}")

                val output = RawFile(outputFile, FileMode.Write)

                ReaderAPI.downloadComic(
                    url,
                    username,
                    password,
                    output,
                    onProgress = { received, total ->
                        viewModelScope.launch(Dispatchers.Main) {
                            val downloadingState = DownloadingState(path, received, total)
                            val state =
                                _browsingState.value.downloadingState.filter { !(it.path == path) }
                                    .toMutableList()
                            state.add(downloadingState)
                            Log.debug(
                                TAG,
                                "Download state update: ${downloadingState.path}=${downloadingState.received}/${downloadingState.total}"
                            )
                            doUpdateDownloadingState(state)
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
                _browsingState.value.downloadingState.filter { !(it.path == path) }
                    .toMutableList()
            doUpdateDownloadingState(finalState)
        }
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
                        entry.size.toLong(),
                        entry.lastModifiedEpoch
                    )
                }.toList()
        _comicBookList.tryEmit(contents)
    }

    private suspend fun doUpdateDownloadingState(state: List<DownloadingState>) {
        val browsingState = _browsingState.value
        _browsingState.emit(
            BrowsingState(
                browsingState.currentPath,
                browsingState.parentPath,
                browsingState.title,
                browsingState.contents,
                state
            )
        )
    }
}
