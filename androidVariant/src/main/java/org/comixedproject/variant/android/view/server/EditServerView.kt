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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import org.comixedproject.variant.android.SERVER_LIST
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.model.Server
import org.comixedproject.variant.platform.Log

private const val TAG = "EditServerView"

private fun validateForm(name: String, url: String): Boolean {
    return !(name.isBlank() || url.isBlank())
}

@Composable
fun EditServerView(
    server: Server, onSave: (Server) -> Unit, onCancel: () -> Unit, modifier: Modifier = Modifier
) {
    var serverName by rememberSaveable { mutableStateOf(server.name) }
    var serverUrl by rememberSaveable { mutableStateOf(server.url) }
    var username by rememberSaveable { mutableStateOf(server.username) }
    var password by rememberSaveable { mutableStateOf(server.password) }
    var validForm by rememberSaveable { mutableStateOf(validateForm(serverName, serverUrl)) }

    Column(modifier = modifier.fillMaxWidth()) {
        TextField(value = serverName, onValueChange = {
            serverName = it
            validForm = validateForm(serverName, serverUrl)
        }, label = {
            Text(
                stringResource(R.string.serverNameLabel)
            )
        }, modifier = Modifier.fillMaxWidth())

        TextField(value = serverUrl, onValueChange = {
            serverUrl = it
            validForm = validateForm(serverName, serverUrl)
        }, label = {
            Text(
                stringResource(R.string.serverUrlLabel)
            )
        }, modifier = Modifier.fillMaxWidth())

        TextField(value = username, onValueChange = {
            username = it
            validForm = validateForm(serverName, serverUrl)
        }, label = {
            Text(
                stringResource(R.string.serverUsernameLabel)
            )
        }, modifier = Modifier.fillMaxWidth())

        TextField(value = password, onValueChange = {
            password = it
            validForm = validateForm(serverName, serverUrl)
        }, label = {
            Text(
                stringResource(R.string.serverPasswordLabel)
            )
        }, modifier = Modifier.fillMaxWidth())

        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(1f))

            Button(onClick = {
                Log.debug(TAG, "Saving server changes: name=${serverName}")
                server.name = serverName
                server.url = serverUrl
                server.username = username
                server.password = password
                onSave(server)
            }, enabled = validForm) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = stringResource(R.string.saveButton)
                )
            }
        }
    }
}

@Composable
@Preview
fun EditServerView_preview_new() {
    VariantTheme { EditServerView(Server(null, "", "", "", ""), onSave = { _ -> }, onCancel = { }) }
}

@Composable
@Preview
fun EditServerView_preview_existing() {
    VariantTheme { EditServerView(SERVER_LIST.get(0), onSave = { _ -> }, onCancel = { }) }
}