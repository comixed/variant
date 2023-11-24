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
import androidx.compose.ui.window.Dialog
import org.comixedproject.variant.R
import org.comixedproject.variant.VariantTheme
import org.comixedproject.variant.model.OPDSServerEntry

/**
 * <code>EditServerDialog</code> manages a dialog for editing an OPDS server.
 *
 * @author Darryl L. Pierce
 */
@Composable
fun EditServerDialog(
    entry: OPDSServerEntry,
    onAdd: (OPDSServerEntry) -> Unit,
    onDismiss: () -> Unit
) = Dialog(onDismissRequest = onDismiss) {
    Surface(
        border = BorderStroke(width = 1.dp, color = Color.Black),
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 8.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp, 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            var name by remember { mutableStateOf(entry.name) }
            var url by remember { mutableStateOf(entry.url) }
            var username by remember { mutableStateOf(entry.username) }
            var password by remember { mutableStateOf(entry.password) }
            var passwordVisible by remember { mutableStateOf(false) }
            var isValid by remember { mutableStateOf(false) }

            OutlinedTextField(
                value = name,
                onValueChange = { input -> name = input },
                label = { Text(stringResource(R.string.server_name_label)) })
            OutlinedTextField(
                value = url,
                onValueChange = { input -> url = input },
                label = { Text(stringResource(R.string.server_url_label)) })
            OutlinedTextField(
                value = username,
                onValueChange = { input -> username = input },
                label = { Text(stringResource(R.string.username_label)) })
            OutlinedTextField(
                value = password,
                onValueChange = { input -> password = input },
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
            Spacer(modifier = Modifier.size(16.dp))
            Row(modifier = Modifier.align(Alignment.End)) {
                Button(onClick = {
                    onDismiss()
                }) {
                    Text(text = stringResource(id = R.string.cancel_button))
                }
                Spacer(modifier = Modifier.size(16.dp))
                Button(onClick = {
                    onAdd(OPDSServerEntry(name, url, username, password, entry.lastAccessedOn))
                }) {
                    Text(text = stringResource(id = R.string.save_button))
                }
            }
        }
    }
}


@Preview
@Composable
fun EditServerDialogAndroidPreview() {
    VariantTheme {
        EditServerDialog(OPDSServerEntry(
            "Server Name",
            "http://www.comixedproject.org:7171/opds",
            "admin@comixedproject.org",
            "password"
        ),
            onAdd = {}, onDismiss = {})
    }
}