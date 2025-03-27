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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import org.comixedproject.variant.android.net.loadServerLinks
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.platform.Logger
import org.comixedproject.variant.shared.viewmodel.ServerActivity
import org.comixedproject.variant.shared.viewmodel.ServerLinkViewModel
import org.comixedproject.variant.shared.viewmodel.ServerViewModel
import org.koin.androidx.compose.koinViewModel

private val TAG = "ServersView"

@Composable
fun ServersView(
    serverViewModel: ServerViewModel = koinViewModel(),
    serverLinkViewModel: ServerLinkViewModel = koinViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val serverList by serverViewModel.serverList.collectAsState()
    val serverActivity by serverViewModel.activity.collectAsState()
    val currentServer by serverViewModel.currentServer.collectAsState()
    var confirmDeletion by rememberSaveable { mutableStateOf(false) }
    val currentDirectory by serverLinkViewModel.currentDirectory.collectAsState()
    val serverLinkList by serverLinkViewModel.serverLinkList.collectAsState()
    var isLoading by rememberSaveable { mutableStateOf(false) }

    if (confirmDeletion) {
        currentServer?.let { server ->
            DeleteServerView(server, onConfirm = {
                Logger.d(TAG, "Deleting server: name=${server.name}")
                serverViewModel.deleteServer(server)
                confirmDeletion = false
            }, onCancel = {
                Logger.d(TAG, "Cancelled deleteing server")
                confirmDeletion = false
            })
        }
    } else {
        when (serverActivity) {
            ServerActivity.LIST_SERVERS -> ServerListView(
                serverList,
                onAddServer = { serverViewModel.addServer() },
                onEditServer = { server -> serverViewModel.editServer(server) },
                onDeleteServer = { server ->
                    serverViewModel.confirmDeleteServer(server)
                    confirmDeletion = true
                },
                onBrowseServer = { server ->
                    serverViewModel.browseServer(server)
                    serverLinkViewModel.loadLinks(server, server.url)
                    if (!serverLinkViewModel.hasLinks(server, server.url)) {
                        isLoading = true
                        coroutineScope.launch {
                            loadServerLinks(
                                server,
                                server.url,
                                onSuccess = { links ->
                                    serverLinkViewModel.saveLinks(
                                        server,
                                        server.url,
                                        links
                                    )
                                    isLoading = false
                                })
                        }
                    } else {
                        serverLinkViewModel.loadLinks(server, server.url)
                    }
                })

            ServerActivity.ADD_SERVER -> EditServerView(
                Server(null, "", "", "", ""),
                onSave = { update -> serverViewModel.saveServer(update) },
                onCancel = { serverViewModel.cancelEditingServer() })

            ServerActivity.EDIT_SERVER -> currentServer?.let { server ->
                EditServerView(
                    server,
                    onSave = { update -> serverViewModel.saveServer(update) },
                    onCancel = { serverViewModel.cancelEditingServer() })
            }

            ServerActivity.BROWSE_SERVER -> currentServer?.let { server ->
                val parentLink = serverLinkViewModel.getParentLink()
                val parentDirectory = when (parentLink == null) {
                    true -> server.url
                    false -> parentLink.downloadLink
                }
                val title = when (parentLink == null) {
                    true -> server.name
                    false -> parentLink.title
                }

                BrowseServerView(
                    server,
                    currentDirectory,
                    parentDirectory,
                    title,
                    isLoading,
                    serverLinkList,
                    onLoadDirectory = { directory, reload ->
                        if (reload || !serverLinkViewModel.hasLinks(server, directory)) {
                            isLoading = true
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
                                        isLoading = false
                                    })
                            }
                        } else {
                            serverLinkViewModel.loadLinks(server, directory)
                        }
                    },
                    onStopBrowsing = { serverViewModel.stopBrowsingServer() })
            }
        }
    }
}