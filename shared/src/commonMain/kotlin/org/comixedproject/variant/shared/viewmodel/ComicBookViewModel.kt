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

import com.rickclephas.kmp.observableviewmodel.MutableStateFlow
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.coroutineScope
import com.rickclephas.kmp.observableviewmodel.launch
import io.github.irgaly.kfswatch.KfsDirectoryWatcher
import io.github.irgaly.kfswatch.KfsDirectoryWatcherEvent
import io.github.irgaly.kfswatch.KfsLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import org.comixedproject.variant.shared.adaptors.getArchiveAdaptor
import org.comixedproject.variant.shared.model.comics.ComicBook
import org.comixedproject.variant.shared.platform.Log

private val TAG = "ComicBookViewModel"

/**
 * <code>ComicBookViewModel</code> provides a view model for working with locally stored comic files.
 *
 * @author Darryl L. Pierce
 */
class ComicBookViewModel() : ViewModel() {
    val archiveAdaptor = getArchiveAdaptor()
    private var _files = MutableStateFlow<List<ComicBook>>(viewModelScope, emptyList())
    val files = _files.asStateFlow()

    lateinit var watcher: KfsDirectoryWatcher

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

    private fun doLoadDirectory(rootDirectory: String) {
        Log.debug(TAG, "Loading contents of directory: ${rootDirectory}")
        val contents = mutableListOf<ComicBook>()

        val path = Path(rootDirectory)
        SystemFileSystem.list(path).forEach { entry ->
            if (archiveAdaptor.isArchive(entry.name)) {
                Log.debug(TAG, "Found file: ${entry.name}")
                contents.add(ComicBook(entry.name))
            }
        }
        _files.tryEmit(contents)
    }
}
