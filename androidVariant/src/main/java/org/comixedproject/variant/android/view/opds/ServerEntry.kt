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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.model.OPDSServer

val TAG_SERVER_NAME = "tag.server-name"
val TAG_SERVER_URL = "tag.server-url"
val TAG_USERNAME = "tag.username"

@Composable
fun ServerEntry(server: OPDSServer, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            modifier = Modifier.testTag(TAG_SERVER_NAME),
            text = server.name,
            maxLines = 1,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            modifier = Modifier.testTag(TAG_SERVER_URL),
            text = server.url,
            maxLines = 1,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            modifier = Modifier.testTag(TAG_USERNAME),
            text = server.username,
            maxLines = 1,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
@Preview
fun ServerEntryPreview() {
    VariantTheme {
        ServerEntry(
            server = OPDSServer(
                "1",
                "Home Server",
                "http://comixedproject.org:7171/opds",
                "admin@comixedproject.org",
                "my!password"
            )
        )
    }
}