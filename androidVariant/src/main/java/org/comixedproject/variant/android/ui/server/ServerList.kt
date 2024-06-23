/*
 * Variant - A digital comic book reading application for the iPad and Android tablets.
 * Copyright (C) 2024, The ComiXed Project
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

package org.comixedproject.variant.android.ui.server

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.shared.model.server.Server

@Composable
fun ServerList(
    servers: List<Server>,
    onServerCreate: () -> Unit,
    onServerSelect: (Server) -> Unit,
    onServerEdit: (Server) -> Unit,
    onServerDelete: (Server) -> Unit
) {
    Scaffold(topBar = {}, bottomBar = {}, floatingActionButton = {
        Button(onClick = onServerCreate) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = stringResource(id = R.string.serverAddButton)
            )
        }
    }) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(servers) { server ->
                ServerListItem(
                    server,
                    onClick = onServerSelect,
                    onEdit = onServerEdit,
                    onDelete = onServerDelete
                )
            }
        }
    }
}

@Preview
@Composable
fun ServerListPreview() {
    VariantTheme {
        ServerList(mutableListOf(
            Server(
                "1",
                "Server 1",
                "http://www.comixedproject.org:7171/opds",
                "reader@comixedprojecvt.org",
                "password"
            ), Server(
                "2",
                "Server 2",
                "http://www.comixedproject.org:7171/opds",
                "reader@comixedprojecvt.org",
                "password"
            ), Server(
                "3",
                "Server 3",
                "http://www.comixedproject.org:7171/opds",
                "reader@comixedprojecvt.org",
                "password"
            ), Server(
                "4",
                "Server 4",
                "http://www.comixedproject.org:7171/opds",
                "reader@comixedprojecvt.org",
                "password"
            ), Server(
                "5",
                "Server 5",
                "http://www.comixedproject.org:7171/opds",
                "reader@comixedprojecvt.org",
                "password"
            )
        ), onServerCreate = {}, onServerSelect = {}, onServerEdit = {}, onServerDelete = {})
    }
}