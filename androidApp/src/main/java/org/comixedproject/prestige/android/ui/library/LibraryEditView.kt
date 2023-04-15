/*
 * Prestige - A digital comic book reading application.
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

package org.comixedproject.prestige.android.ui.library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Title
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.comixedproject.prestige.android.PrestigeTheme
import org.comixedproject.prestige.android.R
import org.comixedproject.prestige.model.library.Library

const val TAG_LIBRARY_NAME = "library.name"
const val TAG_LIBRARY_URL = "library.url"
const val TAG_USERNAME = "library.username"
const val TAG_PASSWORD = "library.password"
const val TAG_SAVE = "library.save"
const val TAG_CANCEL = "library.cancel"

@Composable
fun LibraryEditView(
    library: Library,
    onSave: (String, String, String, String) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    var libraryName by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue(library.name)
        )
    }
    var libraryUrl by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue(library.url)
        )
    }
    var username by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue(library.username)
        )
    }
    var password by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue(library.password)
        )
    }

    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.text_library_edit_title),
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.h2
        )

        Spacer(modifier = Modifier.width(8.dp))

        TextField(
            value = libraryName,
            label = { Text(stringResource(R.string.placeholder_library_name)) },
            leadingIcon = { Icon(imageVector = Icons.Default.Title, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = { name -> libraryName = name }, // onLibraryNameChange,
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TAG_LIBRARY_NAME)
        )
        TextField(
            value = libraryUrl,
            label = { Text(stringResource(R.string.placeholder_library_url)) },
            leadingIcon = { Icon(imageVector = Icons.Default.Computer, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
            onValueChange = { url -> libraryUrl = url },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TAG_LIBRARY_URL)
        )
        TextField(
            value = username,
            label = { Text(stringResource(R.string.placeholder_library_username)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = { name -> username = name },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TAG_USERNAME)
        )
        TextField(
            value = password,
            label = { Text(stringResource(R.string.placeholder_library_password)) },
            leadingIcon = { Icon(imageVector = Icons.Default.Password, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { pwrd -> password = pwrd },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TAG_PASSWORD)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Row {
            Button(
                onClick = {
                    onSave(
                        libraryName.text,
                        libraryUrl.text,
                        username.text,
                        password.text
                    )
                },
                modifier = Modifier.testTag(TAG_SAVE)
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = null)
                Text(stringResource(R.string.button_save))
            }

            Button(
                onClick = onCancel,
                modifier = Modifier.testTag(TAG_CANCEL)
            ) {
                Icon(imageVector = Icons.Default.Cancel, contentDescription = null)
                Text(stringResource(R.string.button_cancel))
            }
        }
    }
}

@Preview
@Composable
fun NewLibraryEditPreview() {
    PrestigeTheme {
        LibraryEditView(
            library = Library(),
            onSave = { name, url, username, password -> {} },
            onCancel = {})
    }
}

@Preview
@Composable
fun LibraryEditPreview() {
    PrestigeTheme {
        LibraryEditView(library = Library(),
            onSave = { name, url, username, password -> {} },
            onCancel = {})
    }
}