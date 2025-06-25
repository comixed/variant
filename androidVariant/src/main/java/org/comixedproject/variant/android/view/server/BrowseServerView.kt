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

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.DIRECTORY_LIST
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.SERVER_LIST
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.model.library.DirectoryEntry
import org.comixedproject.variant.model.state.DownloadingState
import org.comixedproject.variant.platform.Log

private const val TAG = "BrowseServerView"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowseServerView(
    path: String,
    title: String,
    parentPath: String,
    contents: List<DirectoryEntry>,
    downloadingState: List<DownloadingState>,
    isRefreshing: Boolean,
    onLoadDirectory: (String, Boolean) -> Unit,
    onDownloadFile: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val pullToRefreshState = rememberPullToRefreshState()

    Scaffold(
        topBar = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = {
                        Log.debug(TAG, "Going back to parent: ${parentPath}")
                        onLoadDirectory(parentPath, false)
                    },
                    enabled = !parentPath.isEmpty()
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = parentPath)
                }

                val displayedTitle = when (title.isEmpty()) {
                    false -> title
                    true -> stringResource(R.string.rootDirectoryTitle)
                }

                Text(
                    "${displayedTitle} [${downloadingState.size}]",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.headlineMedium, modifier = Modifier.weight(1f)
                )
            }
        },
        content = { padding ->
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = { onLoadDirectory(path, true) },
                indicator = {
                    Indicator(
                        modifier = Modifier.align(Alignment.TopCenter),
                        isRefreshing = isRefreshing,
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        state = pullToRefreshState
                    )
                },
                content = {
                    LazyColumn(
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxWidth()
                    ) {
                        items(contents) { entry ->
                            when (entry.isDirectory) {

                                true -> DirectoryItemView(
                                    entry,
                                    onLoadDirectory = { path, title ->
                                        onLoadDirectory(
                                            path,
                                            false
                                        )
                                    })

                                else -> FileItemView(
                                    entry,
                                    downloadingState,
                                    onDownloadFile = onDownloadFile
                                )
                            }
                        }
                    }
                },
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            )
        },
        modifier = modifier.fillMaxSize()
    )
}

@Composable
@Preview
fun BrowseServerView_preview_directories() {
    val server = SERVER_LIST.get(0)
    val directory = DIRECTORY_LIST.get(0)
    VariantTheme {
        BrowseServerView(
            server.url,
            "",
            "",
            DIRECTORY_LIST.filter { it.isDirectory }, emptyList(), false,
            onLoadDirectory = { _, _ -> }, onDownloadFile = { _, _ -> })
    }
}

@Composable
@Preview
fun BrowseServerView_preview_files() {
    val server = SERVER_LIST.get(0)
    val directory = DIRECTORY_LIST.get(0)
    VariantTheme {
        BrowseServerView(
            server.url,
            directory.title, directory.parent,
            DIRECTORY_LIST.filter { !it.isDirectory }, emptyList(), false,
            onLoadDirectory = { _, _ -> }, onDownloadFile = { _, _ -> })
    }
}