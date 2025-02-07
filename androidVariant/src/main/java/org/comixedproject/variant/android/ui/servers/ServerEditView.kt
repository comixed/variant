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

import android.webkit.URLUtil
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.model.SERVER_LIST
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.platform.Logger

private const val TAG = "ServerEditView"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServerEditView(server: Server, onSave: (Server) -> Unit, onCancel: () -> Unit) {
    val context = LocalContext.current
    var serverName by rememberSaveable { mutableStateOf(server.name) }
    var serverUrl by rememberSaveable { mutableStateOf(server.url) }
    var username by rememberSaveable { mutableStateOf(server.username) }
    var password by rememberSaveable { mutableStateOf(server.password) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.editServerTitle),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            text = stringResource(R.string.serverNameLabel),
            style = MaterialTheme.typography.bodyLarge
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = serverName,
            onValueChange = { serverName = it },
            placeholder = {
                Text(text = stringResource(R.string.serverNamePlaceholder))
            })

        Text(
            text = stringResource(R.string.serverUrlLabel),
            style = MaterialTheme.typography.bodyLarge
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = serverUrl,
            onValueChange = { serverUrl = it },
            placeholder = {
                Text(text = stringResource(R.string.serverUrlPlaceholder))
            })

        Text(
            text = stringResource(R.string.usernameLabel),
            style = MaterialTheme.typography.bodyLarge
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = username,
            onValueChange = { username = it },
            placeholder = {
                Text(text = stringResource(R.string.usernamePlaceholder))
            })

        Text(
            text = stringResource(R.string.passwordLabel),
            style = MaterialTheme.typography.bodyLarge
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { password = it },
            placeholder = {
                Text(text = stringResource(R.string.passwordPlaceholder))
            })

        Spacer(modifier = Modifier.weight(1.0f))

        Row {
            Button(onClick = {
                validateServer(serverName, serverUrl, username, password, onValid = {
                    Logger.d(
                        TAG,
                        "Updating server: name=${serverName} url=${serverUrl} username=${username} password=${password}"
                    )
                    server.name = serverName
                    server.url = serverUrl
                    server.username = username
                    server.password = password
                    onSave(server)
                }, onInvalid = { messageId ->
                    Toast.makeText(
                        context,
                        context.resources.getString(messageId),
                        Toast.LENGTH_SHORT
                    ).show()
                })
            }) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = context.resources.getString(R.string.saveButtonLabel)
                )
            }

            Button(onClick = {
                Logger.d(
                    TAG,
                    "Canceling server edit"
                )
                onCancel()
            }) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = stringResource(R.string.cancelButtonLabel)
                )
            }
        }
    }
}

fun validateServer(
    name: String,
    url: String,
    username: String,
    password: String,
    onValid: (Server) -> Unit, onInvalid: (Int) -> Unit
) {
    if (name.isEmpty()) {
        onInvalid(R.string.invalidServerName)
    }
    if (url.isEmpty() || !URLUtil.isValidUrl(url)) {
        onInvalid(R.string.invalidServerUrl)
    }

    onValid
}

@Composable
@Preview
fun ServerEditPreview() {
    VariantTheme {
        ServerEditView(SERVER_LIST.get(0), onSave = { }, onCancel = {})
    }
}