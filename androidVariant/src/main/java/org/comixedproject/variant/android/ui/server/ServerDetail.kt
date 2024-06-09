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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.shared.model.server.Server

@Composable
fun ServerDetail(
    server: Server,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onBrowse: () -> Unit
) {
    Scaffold(bottomBar = {
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            Button(onClick = onEdit) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = stringResource(id = R.string.editButtonLabel)
                )
            }
            Button(onClick = onBrowse) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(id = R.string.browseButtonLabel)
                )
            }
            Button(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = stringResource(id = R.string.deleteButtonLabel)
                )
            }
        }
    }) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Column(modifier = Modifier.padding(32.dp)) {
                Text(text = server.name, style = MaterialTheme.typography.titleLarge)
                Text(text = server.url, style = MaterialTheme.typography.bodyMedium)
                Text(text = server.username, style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = server.password.replace(".".toRegex(), "*"),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview
@Composable
fun ServerDetailPreview() {
    VariantTheme {
        ServerDetail(
            server = Server(
                "1",
                "My Server",
                "http://www.comixedproject.org:7171/opds",
                "reader@comixedproject.org",
                "my!password"
            ), onEdit = {}, onDelete = {}, onBrowse = {})
    }
}