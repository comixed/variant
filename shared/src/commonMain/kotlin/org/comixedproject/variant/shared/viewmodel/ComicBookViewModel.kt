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

package org.comixedproject.variant.shared.viewmodel

import com.oldguy.common.io.ByteBuffer
import com.oldguy.common.io.File
import com.oldguy.common.io.FileMode
import com.oldguy.common.io.RawFile
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.coroutineScope
import com.rickclephas.kmp.observableviewmodel.launch
import io.github.irgaly.kfswatch.KfsDirectoryWatcher
import io.github.irgaly.kfswatch.KfsDirectoryWatcherEvent
import io.github.irgaly.kfswatch.KfsLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import org.comixedproject.variant.shared.archives.ArchiveAdaptor
import org.comixedproject.variant.shared.model.comics.ComicBook
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.model.server.ServerLink
import org.comixedproject.variant.shared.net.downloadFile
import org.comixedproject.variant.shared.platform.Log

private val TAG = "ComicBookViewModel"

data class DownloadEntry(
    val downloadLink: String,
    var progress: Double
)

/**
 * <code>ComicBookViewModel</code> provides a view model for working with locally stored comic files.
 *
 * @author Darryl L. Pierce
 */
class ComicBookViewModel() : ViewModel() {
    private val archiveAdaptor = ArchiveAdaptor()

    private var _libraryDirectory = MutableStateFlow<String>("")
    val libraryDirectory = _libraryDirectory.asStateFlow()

    private var _comicBookList = MutableStateFlow<List<ComicBook>>(emptyList())
    val comicBookList = _comicBookList.asStateFlow()

    private var _currentDownloads =
        MutableStateFlow<MutableList<DownloadEntry>>(mutableListOf())
    val currentDownloads = _currentDownloads.asStateFlow()

    lateinit var watcher: KfsDirectoryWatcher

    fun setLibraryDirectory(directory: String) {
        Log.debug(TAG, "Setting library directory: ${directory}")
        _libraryDirectory.tryEmit(directory)
    }

    fun watchDirectory(path: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                doLoadDirectory(path)

                Log.debug(TAG, "Watching directory: ${path}")
                watcher =
                    KfsDirectoryWatcher(
                        scope = viewModelScope.coroutineScope,
                        logger = object : KfsLogger {
                            override fun debug(message: String) {
                                Log.debug(TAG, message)
                            }

                            override fun error(message: String) {
                                Log.error(TAG, message)
                            }
                        })
                watcher.add(path)

                watcher.onEventFlow.collect { event: KfsDirectoryWatcherEvent ->
                    Log.debug(TAG, "Received file watcher event: ${event}")
                    doLoadDirectory(event.targetDirectory)
                }
            }
        }
    }

    private suspend fun doLoadDirectory(rootDirectory: String) {
        Log.debug(TAG, "Loading contents of directory: ${rootDirectory}")
        val contents = mutableListOf<ComicBook>()

        val path = File(rootDirectory)
        path.listFiles.forEach { entry ->
            archiveAdaptor.loadComicBook(entry.fullPath)?.let { comicBook ->
                contents.add(comicBook)
            }
        }
        _comicBookList.tryEmit(contents)
    }

    fun download(server: Server, serverLink: ServerLink) {
        viewModelScope.launch {
            Log.info(TAG, "Downloading ${serverLink.downloadLink} => ${serverLink.filename}")
            doAddDownload(serverLink.downloadLink)
            val filename = "${libraryDirectory.value}/${serverLink.filename}"
            downloadFile(
                server,
                serverLink.downloadLink,
                onProgress = { current, total ->
                    Log.debug(
                        TAG,
                        "Downloaded ${current} of ${total} for ${filename}"
                    )
                    doUpdateDownload(serverLink.downloadLink, current, total)
                },
                onCompletion = { bytes ->
                    viewModelScope.launch(Dispatchers.IO) {
                        val file = File(filename)
                        val output = RawFile(file, FileMode.Write)
                        output.write(ByteBuffer(bytes = bytes))
                        output.close()
                    }
                })
            doRemoveDownload(serverLink.downloadLink)
        }
    }

    private fun doAddDownload(downloadLink: String) {
        val currentDownloads = _currentDownloads.value
        currentDownloads.add(DownloadEntry(downloadLink, 0.0))
        _currentDownloads.tryEmit(currentDownloads)
    }

    private fun doUpdateDownload(downloadLink: String, current: Long, total: Long) {
        val currentDownloads =
            _currentDownloads.value.filter { it.downloadLink != downloadLink }.toMutableList()
        val progress = when (total) {
            0L -> 0.0
            else -> current.toDouble() / total
        }
        Log.debug(TAG, "Progress was calculated to ${progress} from ${current} and ${total}")
        _currentDownloads.tryEmit(
            (currentDownloads + DownloadEntry(
                downloadLink,
                progress
            )).toMutableList()
        )
    }

    private fun doRemoveDownload(downloadLink: String) {
        val currentDownloads =
            _currentDownloads.value.filter { it.downloadLink != downloadLink }.toMutableList()
        _currentDownloads.tryEmit(currentDownloads)
    }
}
