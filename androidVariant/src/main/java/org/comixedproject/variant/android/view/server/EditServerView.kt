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
import androidx.compose.material.icons.filled.Clear
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
import org.comixedproject.variant.android.VariantTheme

private const val TAG = "EditServerView"

private fun validateForm(url: String): Boolean {
    return !(url.isBlank())
}

@Composable
fun EditServerView(
    address: String,
    username: String,
    password: String,
    onSave: (String, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var addressValue by rememberSaveable { mutableStateOf(address) }
    var usernameValue by rememberSaveable { mutableStateOf(username) }
    var passwordValue by rememberSaveable { mutableStateOf(password) }
    var validForm by rememberSaveable { mutableStateOf(validateForm(addressValue)) }

    Column(modifier = modifier.fillMaxWidth()) {
        TextField(value = addressValue, onValueChange = {
            addressValue = it
            validForm = validateForm(addressValue)
        }, label = {
            Text(
                stringResource(R.string.serverUrlLabel)
            )
        }, modifier = Modifier.fillMaxWidth())

        TextField(value = usernameValue, onValueChange = {
            usernameValue = it
            validForm = validateForm(addressValue)
        }, label = {
            Text(
                stringResource(R.string.serverUsernameLabel)
            )
        }, modifier = Modifier.fillMaxWidth())

        TextField(value = passwordValue, onValueChange = {
            passwordValue = it
            validForm = validateForm(addressValue)
        }, label = {
            Text(
                stringResource(R.string.serverPasswordLabel)
            )
        }, modifier = Modifier.fillMaxWidth())

        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(1f))

            Button(onClick = {
                onSave(addressValue, usernameValue, passwordValue)
            }, enabled = validForm) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = stringResource(R.string.saveButton)
                )
            }

            Button(onClick = {
                addressValue = address
                usernameValue = username
                passwordValue = password
            }, enabled = validForm) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = stringResource(R.string.cancelButton)
                )
            }
        }
    }
}

@Composable
@Preview
fun EditServerView_preview_new() {
    VariantTheme { EditServerView("", "", "", onSave = { _, _, _ -> }) }
}

@Composable
@Preview
fun EditServerView_preview_existing() {
    VariantTheme {
        EditServerView(
            "hostname:7171",
            "reader@comixproject.org",
            "my!password",
            onSave = { _, _, _ -> })
    }
}