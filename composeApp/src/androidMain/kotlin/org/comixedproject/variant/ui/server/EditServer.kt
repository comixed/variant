/*
 * Variant - A digital comic book reading application for iPad, Android, and desktops.
 * Copyright (C) 2023, The ComiXed Project
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

package org.comixedproject.variant.ui.server

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.comixedproject.variant.R
import org.comixedproject.variant.VariantTheme
import org.comixedproject.variant.data.IdGenerator
import org.comixedproject.variant.model.Server

/**
 * Allows the user to edit an OPDS server.
 *
 * @author Darryl L. Pierce
 */
@Composable
fun EditServer(
    entry: Server,
    onSave: (String, String, String, String, String) -> Unit,
    onCancel: () -> Unit
) {
    Surface(
        border = BorderStroke(width = 1.dp, color = Color.Black),
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 8.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp, 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            var name by remember { mutableStateOf(entry.name) }
            var url by remember { mutableStateOf(entry.url) }
            var username by remember { mutableStateOf(entry.username) }
            var password by remember { mutableStateOf(entry.password) }
            var serverColor by remember { mutableStateOf(entry.serverColor) }
            var passwordVisible by remember { mutableStateOf(false) }
            var isValid by remember { mutableStateOf(false) }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = { input ->
                    name = input
                    isValid = checkValidity(name, url, username, password)
                },
                label = { Text(stringResource(R.string.server_name_label)) })
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = url,
                onValueChange = { input ->
                    url = input
                    isValid = checkValidity(name, url, username, password)
                },
                label = { Text(stringResource(R.string.server_url_label)) })
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = username,
                onValueChange = { input ->
                    username = input
                    isValid = checkValidity(name, url, username, password)
                },
                label = { Text(stringResource(R.string.username_label)) })
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = { input ->
                    password = input
                    isValid = checkValidity(name, url, username, password)
                },
                label = {
                    Text(stringResource(R.string.password_label))
                },
                visualTransformation =
                if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, "")
                    }
                })
            ServerColorPicker(
                currentColor = serverColor,
                onColorPicked = { input ->
                    serverColor = input.hex
                })
            Spacer(
                modifier = Modifier.weight(1.0f)
            )
            Row(
                modifier = Modifier
                    .align(Alignment.End)
            ) {
                Button(onClick = onCancel) {
                    Text(text = stringResource(id = R.string.cancel_button))
                }
                Spacer(modifier = Modifier.size(8.dp))
                Button(onClick = {
                    onSave(name, url, username, password, serverColor)
                }) {
                    Text(text = stringResource(id = R.string.save_button))
                }
            }
        }
    }
}

fun checkValidity(name: String, url: String, username: String, password: String): Boolean {
    return name.isNotBlank() && url.isNotBlank()
}


@Preview
@Composable
fun EditServerAndroidPreview() {
    VariantTheme {
        EditServer(
            Server(
                IdGenerator().toString(),
                "Server Name",
                "http://www.comixedproject.org:7171/opds",
                "admin@comixedproject.org",
                "password"
            ),
            onSave = { _, _, _, _, _ -> {} }, onCancel = {}
        )
    }
}