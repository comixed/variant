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

package org.comixedproject.variant.android.ui.links

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.model.SERVER_LINK_LIST
import org.comixedproject.variant.android.model.SERVER_LIST
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.model.server.ServerLink
import org.comixedproject.variant.shared.platform.Logger

private val TAG = "ServerLinkListView"

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ServerLinkListView(
    server: Server,
    serverLinkList: List<ServerLink>,
    onLoadLink: (ServerLink) -> Unit
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<ServerLink>()

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            LazyColumn {
                items(serverLinkList, key = { it.serverLinkId!! }) { serverLink ->
                    ServerLinkListItemView(
                        serverLink,
                        onLoadLink = { onLoadLink(serverLink) },
                        onShowInfo = {
                            Logger.d(TAG, "Showing details for link: ${serverLink.title}")
                            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, serverLink)
                        }
                    )
                }
            }
        },
        detailPane = {
            navigator.currentDestination?.content?.let { serverLink ->
                ServerLinkDetailView(server, serverLink, onClose = {
                    Logger.d(TAG, "Closing server link details")
                    navigator.navigateTo(ListDetailPaneScaffoldRole.List)
                })
            }
        },
        extraPane = {
            navigator.currentDestination?.content?.let { serverLink ->
                Text("Extra: ${serverLink.title}")
            }
        }
    )
}

@Composable
@Preview
fun ServerLinkListPreview() {
    VariantTheme {
        ServerLinkListView(SERVER_LIST.get(0), SERVER_LINK_LIST, onLoadLink = { _ -> })
    }
}