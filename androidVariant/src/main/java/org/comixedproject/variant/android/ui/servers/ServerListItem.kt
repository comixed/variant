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

package org.comixedproject.variant.android.ui.servers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.model.SERVER_LIST
import org.comixedproject.variant.shared.model.server.Server

@Composable
fun ServerListItem(server: Server, selected: Boolean, onServerSelected: (Server?) -> Unit) {
    val containerColor =
        when (selected) {
            true -> MaterialTheme.colorScheme.surfaceVariant
            else -> MaterialTheme.colorScheme.surface
        }

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .clickable {
                    onServerSelected(
                        when (selected) {
                            true -> null
                            else -> server
                        }
                    )
                }
        ) {
            Text(
                text = "${server.name}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left
            )
            Text(
                text = "${server.url}", style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Left,
                maxLines = 1, overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${server.username}", style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Left
            )
        }
    }
}

@Composable
@Preview
fun ServerListItemPreview_selected() {
    VariantTheme {
        ServerListItem(SERVER_LIST.get(0), true, onServerSelected = { _ -> })
    }
}

@Composable
@Preview
fun ServerListItemPreview_unselected() {
    VariantTheme {
        ServerListItem(SERVER_LIST.get(0), false, onServerSelected = { _ -> })
    }
}