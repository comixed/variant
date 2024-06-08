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

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.model.server.Server

@Composable
fun ServerList(servers: List<Server>, onServerSelect: (Server) -> Unit) {
    LazyColumn {
        items(servers) { server ->
            ServerListItem(server, onClick = onServerSelect)
        }
    }
}

@Preview
@Composable
fun ServerListPreview() {
    VariantTheme {
        ServerList(
            listOf(
                Server(
                    id = 1,
                    name = "My Server",
                    url = "http://www.comixedproject.org:7171/opds",
                    username = "reader@comixedproject.org"
                ), Server(
                    id = 2,
                    name = "My Server",
                    url = "http://www.comixedproject.org:7171/opds",
                    username = "reader@comixedproject.org"
                ), Server(
                    id = 3,
                    name = "My Server",
                    url = "http://www.comixedproject.org:7171/opds",
                    username = "reader@comixedproject.org"
                ), Server(
                    id = 4,
                    name = "My Server",
                    url = "http://www.comixedproject.org:7171/opds",
                    username = "reader@comixedproject.org"
                ), Server(
                    id = 5,
                    name = "My Server",
                    url = "http://www.comixedproject.org:7171/opds",
                    username = "reader@comixedproject.org"
                )
            ),
            onServerSelect = {})
    }
}