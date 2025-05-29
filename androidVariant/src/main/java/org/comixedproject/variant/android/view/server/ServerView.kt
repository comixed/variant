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

package org.comixedproject.variant.android.view.server

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.platform.Log
import org.comixedproject.variant.reader.READER_ROOT
import org.comixedproject.variant.viewmodel.VariantViewModel
import org.koin.androidx.compose.koinViewModel

private const val TAG = "ServerView"

@Composable
fun ServerView(modifier: Modifier = Modifier) {
    val variantViewModel: VariantViewModel = koinViewModel()
    val serverList by variantViewModel.serverList.collectAsState()
    val editing by variantViewModel.editing.collectAsState()
    val editingServer by variantViewModel.editingServer.collectAsState()
    val browsing by variantViewModel.browsing.collectAsState()
    val browsingServer by variantViewModel.browsingServer.collectAsState()
    val currentPath by variantViewModel.currentPath.collectAsState()
    val title by variantViewModel.title.collectAsState()
    val parentPath by variantViewModel.parentPath.collectAsState()
    val directoryContents by variantViewModel.directoryContents.collectAsState()
    val loading by variantViewModel.loading.collectAsState()
    val downloadingState by variantViewModel.downloadingState.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    if (editing) {
        editingServer?.let { server ->
            EditServerView(
                server,
                onSave = { server ->
                    coroutineScope.launch(Dispatchers.IO) {
                        Log.info(TAG, "Saving server: ${server.name} ${server.url}")
                        variantViewModel.saveServer(server)
                        variantViewModel.editServer(null)
                    }
                },
                onCancel = { variantViewModel.editServer(null) },
                modifier = modifier
            )
        }
    } else if (browsing) {
        browsingServer?.let { server ->
            BrowseServerView(
                server,
                currentPath,
                title,
                parentPath,
                directoryContents,
                downloadingState,
                loading,
                modifier = modifier,
                onLoadDirectory = { path, reload ->
                    coroutineScope.launch(Dispatchers.IO) {
                        Log.debug(TAG, "Loading directory: ${path} reload=${reload}")
                        variantViewModel.loadDirectory(server, path, reload)
                    }
                },
                onDownloadFile = { path, filename ->
                    coroutineScope.launch(Dispatchers.IO) {
                        Log.debug(TAG, "Downloading file: ${path} -> ${filename}")
                        variantViewModel.downloadFile(server, path, filename)
                    }
                },
                onStopBrowsing = {
                    coroutineScope.launch(Dispatchers.IO) {
                        variantViewModel.stopBrowsing()
                    }
                },
            )
        }
    } else {
        ServerListView(
            serverList,
            onEditServer = { server -> variantViewModel.editServer(server) },
            onDeleteServer = { _ -> },
            onBrowseServer = { server ->
                coroutineScope.launch(Dispatchers.IO) {
                    Log.info(TAG, "Starting to browse server: ${server.name}")
                    variantViewModel.loadDirectory(server, READER_ROOT, false)
                }
            },
            modifier = modifier
        )
    }
}

@Composable
@Preview
fun ServerView_preview() {
    VariantTheme { ServerView() }
}