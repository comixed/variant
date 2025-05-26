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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.platform.Log
import org.comixedproject.variant.viewmodel.ServerViewModel
import org.koin.androidx.compose.koinViewModel

private const val TAG = "ServerView"

@Composable
fun ServerView(modifier: Modifier = Modifier) {
    val serverViewModel: ServerViewModel = koinViewModel()
    val serverList by serverViewModel.serverList.collectAsState()
    val editingServer by serverViewModel.editingServer.collectAsState()

    if (editingServer != null) {
        editingServer?.let { server ->
            EditServerView(
                server,
                onSave = { server ->
                    Log.info(TAG, "Saving server: ${server.name} ${server.url}")
                    serverViewModel.saveServer(server)
                    serverViewModel.editServer(null)
                },
                onCancel = { serverViewModel.editServer(null) },
                modifier = modifier
            )
        }
    } else {
        ServerListView(
            serverList,
            onEditServer = { server -> serverViewModel.editServer(server) },
            onDeleteServer = { _ -> },
            modifier = modifier
        )
    }
}

@Composable
@Preview
fun ServerView_preview() {
    VariantTheme { ServerView() }
}