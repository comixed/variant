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


@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ServerManagementScreen(
    servers: List<Server>,
    onSaveServer: (Server) -> Unit,
    onBrowserServer: (Server) -> Unit,
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
            ServerList(
                servers,
                onServerCreate = {
                    navigator.navigateTo(
                        ListDetailPaneScaffoldRole.Extra,
                        Server(null, "", "", "", "")
                    )
                },
                onServerSelect = { server ->
                    navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, server)
                },
                onServerDelete = onDeleteServer
            )
        },
        detailPane = {
            navigator.currentDestination?.content?.let { server ->
                ServerDetail(server = server, onEdit = {
                    navigator.navigateTo(ListDetailPaneScaffoldRole.Extra, server)
                }, onBrowse = {
                    onBrowserServer(server)
                }, onDelete = {
                    onDeleteServer(server)
                })
            }
        },
        extraPane = {
            navigator.currentDestination?.content?.let { server ->
                ServerEdit(
                    server = server,
                    onSave = { server ->
                        onSaveServer(server)
                        navigator.navigateTo(ListDetailPaneScaffoldRole.List)
                    },
                    onCancel = { navigator.navigateBack() })
            }
        })
}

@Preview
@Composable
fun ServerManagementScreenPreview() {
    VariantTheme {
        ServerManagementScreen(
            listOf(
                Server(
                    "1",
                    "Server 1",
                    "http://www.comixedproject.org:7171/opds",
                    "reader@comixedprojecvt.org",
                    "password"
                ), Server(
                    "2",
                    "Server 2",
                    "http://www.comixedproject.org:7171/opds",
                    "reader@comixedprojecvt.org",
                    "password"
                ), Server(
                    "3",
                    "Server 3",
                    "http://www.comixedproject.org:7171/opds",
                    "reader@comixedprojecvt.org",
                    "password"
                ), Server(
                    "4",
                    "Server 4",
                    "http://www.comixedproject.org:7171/opds",
                    "reader@comixedprojecvt.org",
                    "password"
                ), Server(
                    "5",
                    "Server 5",
                    "http://www.comixedproject.org:7171/opds",
                    "reader@comixedprojecvt.org",
                    "password"
                )
            ),
            onSaveServer = {},
            onBrowserServer = {},
            onDeleteServer = {}
        )
    }
}