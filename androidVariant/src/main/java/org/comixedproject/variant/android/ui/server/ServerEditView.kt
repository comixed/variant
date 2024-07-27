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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.shared.model.server.Server

/**
 * <code>ServerEditView</code> composes a view for editing a server.
 *
 * @author Darryl L. Pierce
 */
@Composable
fun ServerEditView(
    server: Server,
    onSave: (Long?, String, String, String, String) -> Unit,
    onCancel: () -> Unit,
) {
    val serverId by remember { mutableStateOf(server.serverId) }
    var name by remember { mutableStateOf(server.name) }
    var url by remember { mutableStateOf(server.url) }
    var username by remember { mutableStateOf(server.username) }
    var password by remember { mutableStateOf(server.password) }

    Scaffold(
        floatingActionButton = {
            Button(onClick = { onSave(serverId, name, url, username, password) }) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = stringResource(id = R.string.saveButtonLabel),
                )
            }
        },
    ) { padding ->
        Column(
            modifier =
                Modifier
                    .padding(32.dp)
                    .verticalScroll(rememberScrollState()),
        ) {
            TextField(
                value = name,
                placeholder = { Text(text = stringResource(id = R.string.serverNamePlaceholder)) },
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
            )
            TextField(
                value = url,
                placeholder = { Text(text = stringResource(id = R.string.serverUrlPlaceholder)) },
                onValueChange = { url = it },
                modifier = Modifier.fillMaxWidth(),
            )
            TextField(
                value = username,
                placeholder = { Text(text = stringResource(id = R.string.useramePlaceholder)) },
                onValueChange = { username = it },
                modifier = Modifier.fillMaxWidth(),
            )
            TextField(
                value = password,
                placeholder = { Text(text = stringResource(id = R.string.passwordPlaceholder)) },
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview
@Composable
fun ServerEditPreview_Create() {
    VariantTheme {
        ServerEditView(
            server =
                Server(
                    null,
                    "",
                    "",
                    "",
                    "",
                ),
            onSave = { _, _, _, _, _ -> },
            onCancel = {},
        )
    }
}

@Preview
@Composable
fun ServerEditPreview_Edit() {
    VariantTheme {
        ServerEditView(
            server =
                Server(
                    1L,
                    "My Server",
                    "http://www.comixedproject.org:7171/opds",
                    "reader@comixedproject.org",
                    "my!password",
                ),
            onSave = { _, _, _, _, _ -> },
            onCancel = {},
        )
    }
}
