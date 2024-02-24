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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.model.Server

val TAG_SERVER_ENTRY_BODY = "tag.server-entry.body"
val TAG_SERVER_ENTRY_DELETE_BUTTON = "tag.server-entry.delete-button"

/**
 * <code>ServerEntry</code> shows a single server in a list.
 *
 * @author Darryl L. Pierce
 */
@Composable
fun ServerEntry(
    server: Server,
    onSelect: (Server) -> Unit,
    onDelete: (Server) -> Unit,
    modifier: Modifier = Modifier
) {
    Row {
        Column(
            modifier = modifier
                .testTag(TAG_SERVER_ENTRY_BODY)
                .padding(8.dp)
                .clickable {
                    onSelect(server)
                }
        ) {
            Text(
                text = server.name,
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = server.url,
                maxLines = 1,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = server.username,
                maxLines = 1,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.Rounded.Delete,
            contentDescription = stringResource(id = R.string.deleteServerDescription),
            modifier = Modifier
                .testTag(TAG_SERVER_ENTRY_DELETE_BUTTON)
                .clickable { onDelete(server) }
        )
    }
}

@Composable
@Preview
fun ServerEntryPreview() {
    VariantTheme {
        ServerEntry(
            server = Server(
                "1",
                "Home Server",
                "http://comixedproject.org:7171/opds",
                "admin@comixedproject.org",
                "my!password"
            ),
            onSelect = {},
            onDelete = {}
        )
    }
}