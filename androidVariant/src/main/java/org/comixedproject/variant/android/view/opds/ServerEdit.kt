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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.model.BLANK_SERVER
import org.comixedproject.variant.model.Server

@Composable
fun ServerEdit(server: Server, onSave: (Server) -> Unit, modifier: Modifier = Modifier) {
    var serverId by rememberSaveable { mutableStateOf(server.id) }
    var serverName by rememberSaveable { mutableStateOf(server.name) }
    var serverUrl by rememberSaveable { mutableStateOf(server.url) }
    var serverUsername by rememberSaveable { mutableStateOf(server.username) }
    var serverPassword by rememberSaveable { mutableStateOf(server.password) }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = serverName,
            onValueChange = { name -> serverName = name },
            placeholder = { Text(text = stringResource(id = R.string.editServerNamePlaceholder)) }
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = serverUrl,
            onValueChange = { serverUrl = it },
            placeholder = { Text(text = stringResource(id = R.string.editServerUrlPlaceholder)) }
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = serverUsername,
            onValueChange = { serverUsername = it },
            placeholder = { Text(text = stringResource(id = R.string.editServerUsernamePlaceholder)) }
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = serverPassword,
            onValueChange = { serverPassword = it },
            placeholder = { Text(text = stringResource(id = R.string.editServerPasswordPlaceholder)) }
        )

        Row {
            Spacer(Modifier.weight(1f))

            Button(
                shape = MaterialTheme.shapes.large,
                onClick = {
                    onSave(
                        Server(
                            serverId,
                            serverName,
                            serverUrl,
                            serverUsername,
                            serverPassword
                        )
                    )
                }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = stringResource(id = R.string.serverEditSaveChanges)
                )
            }
        }
    }
}

@Composable
@Preview
fun ServerEditPreviewCreating() {
    VariantTheme {
        ServerEdit(
            server = BLANK_SERVER, onSave = {})
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