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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.net.loadServerLinks
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.platform.Logger
import org.comixedproject.variant.shared.viewmodel.ServerLinkViewModel
import org.comixedproject.variant.shared.viewmodel.ServerViewModel
import org.koin.androidx.compose.koinViewModel

private val TAG = "ServersView"

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ServersView() {
    val serverViewModel: ServerViewModel = koinViewModel<ServerViewModel>()
    val serverLinkViewModel: ServerLinkViewModel = koinViewModel<ServerLinkViewModel>()
    val serverLinkList by serverLinkViewModel.serverLinkList.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val navigator = rememberListDetailPaneScaffoldNavigator<Server>()

    BackHandler(navigator.canNavigateBack()) { navigator.navigateBack() }

    var isEditing by remember { mutableStateOf(false) }
    var isDeleting by remember { mutableStateOf(false) }
    var isBrowsing by remember { mutableStateOf(false) }

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    stringResource(R.string.serverListTitle),
                    style = MaterialTheme.typography.headlineMedium
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(
                        items = serverViewModel.serverList.value,
                        itemContent = { server ->
                            ServerListItem(
                                server,
                                onEditServer = { server ->
                                    isEditing = true
                                    navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, server)
                                },
                                onDeleteServer = { server ->
                                    isDeleting = true
                                    navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, server)
                                },
                                onBrowseServer = { server ->
                                    isBrowsing = true
                                    if (!serverLinkViewModel.hasLinks(server, server.url)) {
                                        coroutineScope.launch {
                                            loadServerLinks(
                                                server,
                                                server.url,
                                                onSuccess = { links ->
                                                    serverLinkViewModel.saveLinks(
                                                        server,
                                                        "",
                                                        links
                                                    )
                                                    navigator.navigateTo(
                                                        ListDetailPaneScaffoldRole.Detail,
                                                        server
                                                    )
                                                },
                                                onFailure = {
                                                    Logger.e(TAG, "Failed to download anyting")
                                                    isBrowsing = false
                                                })
                                        }
                                    } else {
                                        serverLinkViewModel.loadLinks(server, server.url)
                                        navigator.navigateTo(
                                            ListDetailPaneScaffoldRole.Detail,
                                            server
                                        )
                                    }
                                })
                        })
                }
            }
        },
        detailPane = {
            navigator.currentDestination?.content?.let { server ->
                if (isEditing) {
                    Logger.d(
                        TAG,
                        "Editing server: [${server.serverId}] ${server.name}"
                    )
                    ServerEditView(server, onSave = { update ->
                        Logger.d(
                            TAG,
                            "Saving changes to server: id=${update.serverId} ${server.name}"
                        )
                        serverViewModel.saveServer(update)
                        isEditing = false
                    }, onCancel = {
                        Logger.d(TAG, "Canceling changes to server")
                        isEditing = false
                    })
                } else if (isDeleting) {
                    Logger.d(TAG, "Deleting server: [${server.serverId}] ${server.name}")
                } else if (isBrowsing) {
                    Logger.d(TAG, "Browsing server: [${server.serverId}] ${server.name}")
                    BrowseServerView(
                        server,
                        serverLinkViewModel.serverLinkList.value,
                        onFollowLink = { server, directory, reload ->
                            if (reload || !serverLinkViewModel.hasLinks(server, directory)) {
                                coroutineScope.launch {
                                    loadServerLinks(
                                        server,
                                        directory,
                                        onSuccess = { links ->
                                            serverLinkViewModel.saveLinks(
                                                server,
                                                directory,
                                                links
                                            )
                                            serverLinkViewModel.loadLinks(server, directory)
                                        },
                                        onFailure = {
                                            Logger.e(TAG, "Failed to download anyting")
                                            isBrowsing = false
                                        })
                                }
                            } else {
                                serverLinkViewModel.loadLinks(server, directory)
                            }
                        },
                        onStopBrowsing = {
                            isBrowsing = false
                        })
                } else {
                    ServerListItem(
                        server,
                        onEditServer = { server ->
                            isEditing = true
                        },
                        onDeleteServer = { server ->
                            isDeleting = true
                        },
                        onBrowseServer = { server ->
                            isBrowsing = true
                        })
                }
            }
        })
}

@Composable
@Preview
fun ServersPreview() {
    VariantTheme {
        ServersView()
    }
}