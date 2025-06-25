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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.comixedproject.variant.android.DIRECTORY_LIST
import org.comixedproject.variant.android.SERVER_LIST
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.model.library.DirectoryEntry
import org.comixedproject.variant.model.state.DownloadingState

private const val TAG = "DirectoryDetailView"

@Composable
fun FileItemView(
    fileEntry: DirectoryEntry,
    downloadingState: List<DownloadingState>,
    onDownloadFile: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val downloading = downloadingState
        .filter { it.path == fileEntry.path }
        .firstOrNull()


    ElevatedCard(
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = {
                        onDownloadFile(fileEntry.path, fileEntry.filename)
                    },
                    enabled = downloadingState.size < 5
                ) {
                    Icon(Icons.Filled.AddCircle, contentDescription = fileEntry.title)
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "${fileEntry.title}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${fileEntry.filename}",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    downloading?.let {
                        val received = it.received
                        val total = it.total
                        val progress = when (received > 0) {
                            true -> (received.toFloat() / total.toFloat())
                            false -> 0.0
                        }
                        LinearProgressIndicator(
                            progress = { progress.toFloat() },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun FileItemView_preview() {
    VariantTheme {
        FileItemView(
            DIRECTORY_LIST.filter { !it.isDirectory }.first(),
            listOf(),
            onDownloadFile = { _, _ -> })
    }
}

@Composable
@Preview
fun FileItemView_preview_downloading() {
    val fileEntry = DIRECTORY_LIST.filter { !it.isDirectory }.first()
    val server = SERVER_LIST.first()
    VariantTheme {
        FileItemView(
            fileEntry,
            listOf(
                DownloadingState(
                    fileEntry.path,
                    50, 100
                )
            ),
            onDownloadFile = { _, _ -> })
    }
}