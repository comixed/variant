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

import androidx.compose.foundation.layout.fillMaxSize
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
import org.comixedproject.variant.android.model.SERVER_LIST
import org.comixedproject.variant.shared.model.server.Server

@Composable
fun ServerListView(
    serverList: List<Server>,
    currentServer: Server?,
    onSelectServer: (Server?) -> Unit
) {
    Scaffold(floatingActionButton = {
        Button(onClick = {}) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = stringResource(R.string.serverAddButton)
            )
        }
    }) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(serverList) { server ->
                ServerListItem(
                    server,
                    server == currentServer,
                    onServerSelected = { selection -> onSelectServer(selection) })
            }
        }
    }
}

@Composable
@Preview
fun ServerListPreview_noSelection() {
    VariantTheme {
        ServerListView(SERVER_LIST, null, onSelectServer = { _ -> })
    }
}

@Composable
@Preview
fun ServerListPreview_hasSelection() {
    VariantTheme {
        ServerListView(SERVER_LIST, SERVER_LIST.get(2), onSelectServer = { _ -> })
    }
}