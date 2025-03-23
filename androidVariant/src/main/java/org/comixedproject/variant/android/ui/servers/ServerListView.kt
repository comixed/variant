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

package org.comixedproject.variant.android.ui.servers

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.model.SERVER_LIST
import org.comixedproject.variant.shared.model.server.Server

private val TAG = "ServersView"

val TAG_ADD_BUTTON = "button.add-server"

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ServerListView(
    serverList: List<Server>,
    onAddServer: () -> Unit,
    onEditServer: (Server) -> Unit,
    onDeleteServer: (Server) -> Unit,
    onBrowseServer: (Server) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    stringResource(R.string.serverListTitle),
                    style = MaterialTheme.typography.titleLarge
                )
            })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddServer,
                modifier = Modifier.testTag(TAG_ADD_BUTTON)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = stringResource(R.string.serverAddButton)
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
        ) {
            items(
                items = serverList,
                itemContent = { server ->
                    ServerListItemView(
                        server,
                        onEditServer = onEditServer,
                        onDeleteServer = onDeleteServer,
                        onBrowseServer = onBrowseServer
                    )
                })
        }
    }
}

@Composable
@Preview
fun ServerListPreview() {
    VariantTheme {
        ServerListView(
            SERVER_LIST,
            onAddServer = { },
            onEditServer = { _ -> },
            onDeleteServer = { _ -> },
            onBrowseServer = { _ -> })
    }
}