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

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.model.SERVER_LIST
import org.comixedproject.variant.android.net.loadServerLinks
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.model.server.ServerLinkType
import org.comixedproject.variant.shared.platform.Logger
import org.comixedproject.variant.shared.viewmodel.ServerLinkViewModel
import org.koin.androidx.compose.koinViewModel

private val TAG = "BrowseServerView"

data class DirectoryEntry(
    val title: String,
    val directory: String
)

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun BrowseServerView(
    server: Server,
    onStopBrowsing: () -> Unit
) {
    val viewModel: ServerLinkViewModel = koinViewModel<ServerLinkViewModel>()
    val serverLinkList by viewModel.serverLinkList.collectAsState()
    var directory by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val directoryStack = remember { mutableStateListOf<DirectoryEntry>() }

    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()

    BackHandler(navigator.canNavigateBack()) { navigator.navigateBack() }

    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row {
                    if (directoryStack.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                            contentDescription = stringResource(
                                R.string.navigateBackLabel
                            ),
                            modifier = Modifier.clickable {
                                Logger.d(TAG, "Navigating back one level")
                                directoryStack.removeLast()
                                val path = when (directoryStack.isEmpty()) {
                                    true -> server.url
                                    false -> directoryStack.last().directory
                                }
                                if (!viewModel.hasLinks(server, path)) {
                                    coroutineScope.launch {
                                        loadServerLinks(
                                            server,
                                            path,
                                            onSuccess = { links ->
                                                viewModel.saveLinks(
                                                    server,
                                                    path,
                                                    links
                                                )
                                                viewModel.loadLinks(server, path)
                                                directory = path
                                                navigator.navigateTo(
                                                    ListDetailPaneScaffoldRole.List,
                                                    path
                                                )
                                            },
                                            onFailure = {
                                                Logger.e(TAG, "Failed to download anyting")
                                                onStopBrowsing()
                                            })
                                    }
                                } else {
                                    viewModel.loadLinks(server, path)
                                    directory = path
                                    navigator.navigateTo(
                                        ListDetailPaneScaffoldRole.List,
                                        path
                                    )
                                }
                            }
                        )
                    }
                    val label = when (directoryStack.isEmpty()) {
                        true -> server.name
                        false -> directoryStack.last().title
                    }
                    Text(
                        "${label}",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        items = serverLinkList,
                        itemContent = { serverLink ->
                            if (serverLink.linkType == ServerLinkType.NAVIGATION) {
                                NavigationLinkView(serverLink, onLoadLink = { path ->
                                    directoryStack.add(DirectoryEntry(serverLink.title, path))
                                    if (!viewModel.hasLinks(server, path)) {
                                        coroutineScope.launch {
                                            loadServerLinks(
                                                server,
                                                path,
                                                onSuccess = { links ->
                                                    viewModel.saveLinks(
                                                        server,
                                                        path,
                                                        links
                                                    )
                                                    viewModel.loadLinks(server, path)
                                                    directory = path
                                                    navigator.navigateTo(
                                                        ListDetailPaneScaffoldRole.List,
                                                        path
                                                    )
                                                },
                                                onFailure = {
                                                    Logger.e(TAG, "Failed to download anyting")
                                                    onStopBrowsing()
                                                })
                                        }
                                    } else {
                                        viewModel.loadLinks(server, path)
                                        directory = path
                                        navigator.navigateTo(
                                            ListDetailPaneScaffoldRole.List,
                                            path
                                        )
                                    }
                                })
                            } else {
                                PublicationLinkView(serverLink, onLoadLink = { path ->
                                    directoryStack.add(DirectoryEntry(serverLink.title, path))
                                    directory = path
                                    // TODO load the publication
                                    navigator.navigateTo(
                                        ListDetailPaneScaffoldRole.Detail,
                                        serverLink
                                    )
                                })
                            }
                        }
                    )
                }
            }
        },
        detailPane = {
            Logger.d(TAG, "Showing the detail pane: ${navigator.currentDestination}")
            if (directoryStack.isNotEmpty()) {
                Text("Detail pane! ${directoryStack.last().title}")
            }
        }
    )
}

@Composable
@Preview
fun BrowseServerPreview() {
    VariantTheme {
        BrowseServerView(
            SERVER_LIST.get(0),
            onStopBrowsing = { })
    }
}