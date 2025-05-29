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

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.SERVER_LIST
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.model.Server
import org.comixedproject.variant.platform.Log

private const val TAG = "ServerListView"

@Composable
fun ServerListView(
    serverList: List<Server>,
    onEditServer: (Server) -> Unit,
    onDeleteServer: (Server) -> Unit,
    onBrowseServer: (Server) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        content = { padding ->
            if (serverList.isEmpty()) {
                Log.debug(TAG, "No servers to display")
                Text(stringResource(R.string.emptyServerListText))
            } else {
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize(),
                    contentPadding = padding
                ) {
                    items(serverList) { server ->
                        Log.debug(TAG, "Showing server: ${server.name}")
                        ServerListItemView(
                            server,
                            onEditServer = onEditServer,
                            onDeleteServer = onDeleteServer,
                            onServerClicked = onBrowseServer
                        )
                    }
                }
            }
        }, floatingActionButton = {
            IconButton(onClick = { onEditServer(Server(null, "", "", "", "")) }) {
                Icon(Icons.Default.Add, stringResource(R.string.addServerDescription))
            }
        }, modifier = modifier
    )
}

@Composable
@Preview
fun ServerListView_emptyList() {
    VariantTheme {
        ServerListView(
            emptyList(),
            onEditServer = { _ -> },
            onDeleteServer = { _ -> },
            onBrowseServer = { _ -> })
    }
}

@Composable
@Preview
fun ServerListView_withItems() {
    VariantTheme {
        ServerListView(
            SERVER_LIST,
            onEditServer = { _ -> },
            onDeleteServer = { _ -> },
            onBrowseServer = { _ -> })
    }
}