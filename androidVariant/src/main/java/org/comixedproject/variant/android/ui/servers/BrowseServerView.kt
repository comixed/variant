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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.ui.links.ServerLinkDetailView
import org.comixedproject.variant.android.ui.links.ServerLinkListView
import org.comixedproject.variant.shared.model.SERVER_LINK_LIST
import org.comixedproject.variant.shared.model.SERVER_LIST
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.model.server.ServerLink
import org.comixedproject.variant.shared.model.server.ServerLinkType
import org.comixedproject.variant.shared.platform.Log

private const val TAG = "BrowseServerView"

const val TAG_BACK_NAVIGATION_BUTTON = "back-navigation"
const val TAG_STOP_NAVIGATING_BUTTON = "stop-navigating"
const val TAG_REFRESH = "refresh-box"

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun BrowseServerView(
    server: Server,
    currentDirectory: String,
    parentDirectory: String,
    title: String,
    isLoading: Boolean,
    serverLinkList: List<ServerLink>,
    onLoadDirectory: (String, Boolean) -> Unit,
    onStopBrowsing: () -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()
    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<Any>()
    val coroutineScope = rememberCoroutineScope()

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
                                Log.debug(TAG, "Going back to parent: ${parentDirectory}")
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
                },
                actions = {
                    IconButton(
                        onClick = {
                            Log.info(TAG, "Stopping browsing")
                            onStopBrowsing()
                        },
                        modifier = Modifier.testTag(TAG_STOP_NAVIGATING_BUTTON)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.stopBrowsingLabel)
                        )
                    }
                })
        },
        content = { padding ->
            NavigableListDetailPaneScaffold(
                navigator = scaffoldNavigator,
                listPane = {
                    PullToRefreshBox(
                        modifier = Modifier
                            .padding(padding)
                            .testTag(TAG_REFRESH),
                        isRefreshing = isLoading,
                        state = pullToRefreshState,
                        onRefresh = {
                            Log.info(
                                TAG,
                                "Reloading directory: ${parentDirectory}"
                            )
                            onLoadDirectory(currentDirectory, true)
                        }, content = {
                            ServerLinkListView(
                                server,
                                serverLinkList.sortedBy { link -> link.title },
                                onLoadLink = { link ->
                                    when (link.linkType) {
                                        ServerLinkType.NAVIGATION -> onLoadDirectory(
                                            link.downloadLink,
                                            false
                                        )

                                        ServerLinkType.PUBLICATION ->
                                            coroutineScope.launch {
                                                withContext(Dispatchers.IO) {
                                                    Log.info(
                                                        TAG,
                                                        "Showing link details: ${link.downloadLink}"
                                                    )
                                                    scaffoldNavigator.navigateTo(
                                                        ListDetailPaneScaffoldRole.Detail,
                                                        link
                                                    )
                                                }
                                            }
                                    }
                                })
                        }
                    )
                },
                detailPane = {
                    scaffoldNavigator.currentDestination?.content?.let { link ->
                        ServerLinkDetailView(
                            server,
                            link as ServerLink,
                            onClose = { scaffoldNavigator.navigateBack() })
                    }
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
            onLoadDirectory = { _, _ -> },
            onStopBrowsing = { })
    }
}