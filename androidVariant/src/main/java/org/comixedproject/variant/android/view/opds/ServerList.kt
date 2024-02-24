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

package org.comixedproject.variant.android.view.opds

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.model.Server

val TAG_SERVER_LIST_COLUMN = "tag.server-list-column"

@Composable
fun ServerList(
    serverList: List<Server>,
    onSelect: (Server) -> Unit,
    onCreate: () -> Unit,
    onDelete: (Server) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {},
        bottomBar = {},
        floatingActionButton = {
            Button(onClick = onCreate) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(id = R.string.createServerButton)
                )
            }
        },
    ) { padding ->
        LazyColumn(
            modifier = modifier
                .padding(padding)
                .testTag(TAG_SERVER_LIST_COLUMN)
        ) {
            items(serverList) { server ->
                ServerEntry(server, onSelect = { onSelect(it) }, onDelete = { onDelete(it) })
            }
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
                    "1",
                    "Server 1",
                    "http://comixedproject.org:7171/opds",
                    "admin@comixedproject.org",
                    "my!password"
                ),
                Server(
                    "2",
                    "Server 2",
                    "http://comixedproject.org:7171/opds",
                    "admin@comixedproject.org",
                    "my!password"
                ),
                Server(
                    "3",
                    "Server 3",
                    "http://comixedproject.org:7171/opds",
                    "admin@comixedproject.org",
                    "my!password"
                ),
                Server(
                    "4",
                    "Server 4",
                    "http://comixedproject.org:7171/opds",
                    "admin@comixedproject.org",
                    "my!password"
                ),
                Server(
                    "5",
                    "Server 5",
                    "http://comixedproject.org:7171/opds",
                    "admin@comixedproject.org",
                    "my!password"
                ),
            ),
            onSelect = {},
            onCreate = {},
            onDelete = {}
        )
    }
}