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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.comixedproject.variant.android.DIRECTORY_LIST
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.view.BYTES_PER_MB
import org.comixedproject.variant.model.library.DirectoryEntry
import org.comixedproject.variant.model.state.DownloadingState

private const val TAG = "DirectoryDetailView"

@Composable
fun FileItemView(
    fileEntry: DirectoryEntry,
    comicBookFilenameList: List<String>,
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
                if (downloading == null) {
                    if (comicBookFilenameList.contains(fileEntry.filename)) {
                        IconButton(
                            onClick = { },
                            enabled = downloadingState.size < 5
                        ) {
                            Icon(
                                painterResource(id = R.drawable.ic_downloaded_file),
                                contentDescription = fileEntry.title
                            )
                        }
                    } else {
                        IconButton(
                            onClick = {
                                onDownloadFile(fileEntry.path, fileEntry.filename)
                            },
                            enabled = downloadingState.size < 5
                        ) {
                            Icon(
                                painterResource(R.drawable.ic_download_file),
                                contentDescription = fileEntry.title
                            )
                        }
                    }
                } else {
                    IconButton(
                        onClick = { },
                        enabled = downloadingState.size < 5
                    ) {
                        Icon(
                            painterResource(R.drawable.ic_downloading_file),
                            contentDescription = fileEntry.title
                        )
                    }
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

                    if (downloading == null) {
                        Text(
                            text = "${fileEntry.filename}",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Left,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    } else {
                        val received = downloading.received
                        val total = downloading.total
                        val progress = when (received > 0) {
                            true -> (received.toFloat() / total.toFloat())
                            false -> 0.0
                        }
                        val fileSize = String.format("%.1f", fileEntry.fileSize / BYTES_PER_MB)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("${fileSize} MB", style = MaterialTheme.typography.bodySmall)
                            LinearProgressIndicator(
                                progress = { progress.toFloat() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun FileItemViewPreview() {
    val fileEntry = DIRECTORY_LIST.filter { !it.isDirectory }.first()
    VariantTheme {
        FileItemView(
            fileEntry,
            listOf(),
            listOf(),
            onDownloadFile = { _, _ -> })
    }
}

@Composable
@Preview
fun FileItemViewPreviewDownloaded() {
    val fileEntry = DIRECTORY_LIST.filter { !it.isDirectory }.first()
    VariantTheme {
        FileItemView(
            fileEntry,
            listOf(fileEntry.filename),
            listOf(),
            onDownloadFile = { _, _ -> })
    }
}

@Composable
@Preview
fun FileItemViewPreviewDownloading() {
    val fileEntry = DIRECTORY_LIST.filter { !it.isDirectory }.first()
    VariantTheme {
        FileItemView(
            fileEntry,
            listOf(),
            listOf(
                DownloadingState(
                    fileEntry.path, fileEntry.filename,
                    50, 100
                )
            ),
            onDownloadFile = { _, _ -> })
    }
}