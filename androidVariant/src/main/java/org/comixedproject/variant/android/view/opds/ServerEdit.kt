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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.model.Server

@Composable
fun ServerEdit(server: Server, onSave: (Server) -> Unit, modifier: Modifier = Modifier) {
    var serverId by remember { mutableStateOf(server.id) }
    var serverName by remember { mutableStateOf(server.name) }
    var serverUrl by remember { mutableStateOf(server.url) }
    var serverUsername by remember { mutableStateOf(server.username) }
    var serverPassword by remember { mutableStateOf(server.password) }

    Scaffold(topBar = {}, bottomBar = {}, floatingActionButton = {
        FloatingActionButton(onClick = {
            onSave(Server(serverId, serverName, serverUrl, serverUsername, serverPassword))
        }) {
            Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = stringResource(id = R.string.serverEditSaveChanges)
            )
        }
    }) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            TextField(
                value = serverName,
                onValueChange = { serverName = it },
//                placeholder = Text(text = stringResource(id = R.string.editServerNamePlaceholder)),
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = serverUrl,
                onValueChange = { serverUrl = it },
//                placeholder = Text(text = stringResource(id = R.string.editServerUrlPlaceholder)),
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = serverUsername,
                onValueChange = { serverUsername = it },
//                placeholder = Text(text = stringResource(id = R.string.editServerUsernamePlaceholder)),
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = serverPassword,
                onValueChange = { serverPassword = it },
//                placeholder = Text(text = stringResource(id = R.string.editServerPasswordPlaceholder)),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
@Preview
fun ServerEditPreviewCreating() {
    VariantTheme {
        ServerEdit(
            server = Server(
                "1",
                "Home Server",
                "http://comixedproject.org:7171/opds",
                "admin@comixedproject.org",
                "my!password"
            ), onSave = {})
    }
}

@Composable
@Preview
fun ServerEditPreviewEditing() {
    VariantTheme {
        ServerEdit(
            server = Server(
                "1",
                "Home Server",
                "http://comixedproject.org:7171/opds",
                "admin@comixedproject.org",
                "my!password"
            ), onSave = {})
    }
}