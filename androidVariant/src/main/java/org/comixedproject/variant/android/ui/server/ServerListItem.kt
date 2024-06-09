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

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.ui.DismissBackground
import org.comixedproject.variant.shared.model.server.Server

@Composable
fun ServerListItem(server: Server, onClick: (Server) -> Unit, onDelete: (Server) -> Unit) {
    val context = LocalContext.current
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.StartToEnd -> onDelete(server)
                else -> return@rememberSwipeToDismissBoxState false
            }

            return@rememberSwipeToDismissBoxState true
        },
        positionalThreshold = { it * .25f }
    )

    SwipeToDismissBox(state = dismissState,
        modifier = Modifier,
        backgroundContent = { DismissBackground(dismissState) },
        content = {
            ListItem(
                leadingContent = { Icon(Icons.Filled.Info, contentDescription = server.name) },
                overlineContent = { Text(server.url) },
                headlineContent = { Text(server.name) },
                supportingContent = { Text(server.username) },
                modifier = Modifier.clickable {
                    onClick(server)
                }
            )
        })
}

@Preview
@Composable
fun ServerListItemPreview() {
    VariantTheme {
        ServerListItem(
            server = Server(
                "1",
                "Server 1",
                "http://www.comixedproject.org:7171/opds",
                "reader@comixedprojecvt.org",
                "password"
            ),
            onClick = {},
            onDelete = {}
        )
    }
}