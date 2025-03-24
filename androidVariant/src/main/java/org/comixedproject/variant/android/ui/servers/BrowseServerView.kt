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

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.model.SERVER_LINK_LIST
import org.comixedproject.variant.android.model.SERVER_LIST
import org.comixedproject.variant.android.ui.links.ServerLinkListView
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.model.server.ServerLink
import org.comixedproject.variant.shared.platform.Logger

private const val TAG = "BrowseServerView"

const val TAG_BACK_NAVIGATION_BUTTON = "back-navigation"
const val TAG_REFRESH = "refresh-box"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowseServerView(
    server: Server,
    currentDirectory: String,
    parentDirectory: String,
    title: String,
    isLoading: Boolean,
    serverLinkList: List<ServerLink>,
    onLoadDirectory: (String, Boolean) -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                navigationIcon = {
                    if (parentDirectory != currentDirectory) {
                        IconButton(
                            onClick = {
                                Logger.d(TAG, "Going back to parent: ${parentDirectory}")
                                onLoadDirectory(parentDirectory, false)
                            },
                            modifier = Modifier.testTag(TAG_BACK_NAVIGATION_BUTTON)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = stringResource(R.string.navigateBackLabel)
                            )
                        }
                    }
                })
        },
        content = { padding ->
            PullToRefreshBox(
                modifier = Modifier
                    .padding(padding)
                    .testTag(TAG_REFRESH),
                isRefreshing = isLoading,
                state = pullToRefreshState,
                onRefresh = {
                    Logger.d(
                        TAG,
                        "Reloading directory: ${parentDirectory}"
                    )
                    onLoadDirectory(currentDirectory, true)
                }, content = {
                    ServerLinkListView(
                        server,
                        serverLinkList.sortedBy { link -> link.title },
                        onLoadLink = { link -> onLoadDirectory(link.downloadLink, false) })
                }
            )
        }
    )
}

@Composable
@Preview
fun BrowseServerPreview() {
    VariantTheme {
        BrowseServerView(
            SERVER_LIST.get(0),
            SERVER_LINK_LIST.get(0).downloadLink,
            SERVER_LINK_LIST.get(0).directory,
            SERVER_LIST.get(0).name,
            false,
            SERVER_LINK_LIST,
            onLoadDirectory = { _, _ -> })
    }
}