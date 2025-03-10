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

package org.comixedproject.variant.android.ui.home

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.model.NavigationTarget
import org.comixedproject.variant.android.model.SERVER_LINK_LIST
import org.comixedproject.variant.android.model.SERVER_LIST
import org.comixedproject.variant.android.ui.comics.ComicView
import org.comixedproject.variant.android.ui.servers.BrowseServerView
import org.comixedproject.variant.android.ui.servers.ServerDetailView
import org.comixedproject.variant.android.ui.servers.ServerEditView
import org.comixedproject.variant.android.ui.servers.ServerListView
import org.comixedproject.variant.android.ui.setings.SettingsView
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.model.server.ServerLink

@Composable
fun HomeView(
    serverList: List<Server>,
    serverLinkList: List<ServerLink>,
    onSaveServer: (Server) -> Unit,
    onDeleteServer: (Server) -> Unit,
    onLoadLinks: (Server, String, Boolean) -> Unit
) {
    var currentDestination by rememberSaveable { mutableStateOf(NavigationTarget.COMICS) }
    var currentServer by remember { mutableStateOf<Server?>(null) }
    var editServer by remember { mutableStateOf(false) }
    var browseServer by remember { mutableStateOf(false) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            NavigationTarget.entries.forEach { target ->
                item(
                    icon = {
                        Icon(
                            target.icon,
                            contentDescription = stringResource(target.contentDescription)
                        )
                    },
                    label = { Text(stringResource(target.label)) },
                    selected = target == currentDestination,
                    onClick = { currentDestination = target })
            }
        },
    ) {
        when (currentDestination) {
            NavigationTarget.SERVERS ->
                currentServer.let { server ->
                    if (server != null) {
                        if (editServer) {
                            ServerEditView(
                                server,
                                onSave = { update ->
                                    onSaveServer(update)
                                    currentServer = null
                                    editServer = false
                                },
                                onCancel = {
                                    currentServer = null
                                    editServer = false
                                })
                        } else if (browseServer) {
                            BrowseServerView(
                                server,
                                serverLinkList,
                                onFollowLink = onLoadLinks,
                                onStopBrowsing = {
                                    browseServer = false
                                    currentServer = null
                                }
                            )
                        } else {
                            ServerDetailView(server)
                        }
                    } else {
                        ServerListView(
                            serverList, onEditServer = { target ->
                                currentServer = target
                                editServer = true
                            },
                            onDeleteServer = onDeleteServer,
                            onBrowseServer = { target ->
                                currentServer = target
                                browseServer = true
                                onLoadLinks(target, target.url, false)
                            }
                        )
                    }
                }

            NavigationTarget.COMICS -> ComicView()
            NavigationTarget.SETTINGS -> SettingsView()
        }
    }
}

@Composable
@Preview
fun HomePreview() {
    VariantTheme {
        HomeView(
            SERVER_LIST,
            SERVER_LINK_LIST,
            onSaveServer = { _ -> },
            onDeleteServer = { _ -> },
            onLoadLinks = { _, _, _ -> })
    }
}