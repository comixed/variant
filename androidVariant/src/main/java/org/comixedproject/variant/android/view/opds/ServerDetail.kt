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
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.model.Server

val TAG_SERVER_DETAIL_NAME = "tag.server-detail.name"
val TAG_SERVER_DETAIL_URL = "tag.server-detail.url"
val TAG_SERVER_DETAIL_USERNAME = "tag.server-detail.username"
val TAG_SERVER_DETAIL_PASSWORD = "tag.server-detail.password"

@Composable
fun ServerDetail(
    server: Server,
    onEdit: (Server) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { },
        bottomBar = { },
        floatingActionButton = {
            FloatingActionButton(onClick = { onEdit(server) }) {
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = stringResource(id = R.string.editServerDescription)
                )
            }
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Text(
                text = server.name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(TAG_SERVER_DETAIL_NAME)
            )
            Text(
                text = stringResource(id = R.string.serverUrlDescription, server.url),
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(TAG_SERVER_DETAIL_URL)
            )
            Text(
                text = stringResource(id = R.string.serverUsernameDescription, server.username),
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(TAG_SERVER_DETAIL_USERNAME)
            )
            Text(
                text = stringResource(id = R.string.serverPasswordDescription, server.password),
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(TAG_SERVER_DETAIL_PASSWORD)
            )
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
                "My Home Server",
                "http://comixedprojecdt.org:7171/opds",
                "admin@comixedproject.org",
                "my!password"
            ),
            onEdit = {}
        )
    }
}