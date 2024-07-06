/*
 * Variant - A digital comic book reading application for the iPad and Android tablets.
 * Copyright (C) 2024, The ComiXed Project
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

package org.comixedproject.variant.android.ui.server

import androidx.activity.compose.BackHandler
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.shared.model.server.Server

/**
 * <code>ServerManagementView</code> composes a view for managing the list of servers.
 *
 * @author Darryl L. Pierce
 */
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ServerManagementView(
    servers: List<Server>,
    onSaveServer: (Long?, String, String, String, String) -> Unit,
    onBrowseServer: (Server) -> Unit,
    onDeleteServer: (Server) -> Unit
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Server>()

    BackHandler {
        navigator.navigateBack()
    }

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            ServerListView(
                servers,
                onCreateServer = {
                    navigator.navigateTo(
                        ListDetailPaneScaffoldRole.Extra,
                        Server(null, "", "", "", "")
                    )
                },
                onBrowseServer = onBrowseServer,
                onEditServer = { server ->
                    navigator.navigateTo(
                        ListDetailPaneScaffoldRole.Extra,
                        server
                    )
                },
                onDeleteServer = onDeleteServer
            )
        },
        detailPane = {
            navigator.currentDestination?.content?.let { server ->
                ServerDetailView(server = server)
            }
        },
        extraPane = {
            navigator.currentDestination?.content?.let { server ->
                ServerEditView(
                    server = server,
                    onSave = { serverId, name, url, username, password ->
                        onSaveServer(serverId, name, url, username, password)
                        navigator.navigateBack()
                    },
                    onCancel = { navigator.navigateBack() })
            }
        })
}

@Preview
@Composable
fun ServerManagementPreview() {
    VariantTheme {
        ServerManagementView(
            listOf(
                Server(
                    1L,
                    "Server 1",
                    "http://www.comixedproject.org:7171/opds",
                    "reader@comixedprojecvt.org",
                    "password"
                ), Server(
                    2L,
                    "Server 2",
                    "http://www.comixedproject.org:7171/opds",
                    "reader@comixedprojecvt.org",
                    "password"
                ), Server(
                    3L,
                    "Server 3",
                    "http://www.comixedproject.org:7171/opds",
                    "reader@comixedprojecvt.org",
                    "password"
                ), Server(
                    4L,
                    "Server 4",
                    "http://www.comixedproject.org:7171/opds",
                    "reader@comixedprojecvt.org",
                    "password"
                ), Server(
                    5L,
                    "Server 5",
                    "http://www.comixedproject.org:7171/opds",
                    "reader@comixedprojecvt.org",
                    "password"
                )
            ),
            onSaveServer = { _, _, _, _, _ -> },
            onBrowseServer = {},
            onDeleteServer = {}
        )
    }
}