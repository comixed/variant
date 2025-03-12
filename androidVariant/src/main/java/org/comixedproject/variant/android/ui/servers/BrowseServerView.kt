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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.model.SERVER_LINK_LIST
import org.comixedproject.variant.android.model.SERVER_LIST
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.model.server.ServerLink
import org.comixedproject.variant.shared.model.server.ServerLinkType
import org.comixedproject.variant.shared.platform.Logger

private val TAG = "BrowseServerView"

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun BrowseServerView(
    server: Server,
    title: String,
    serverLinkList: List<ServerLink>,
    onLoadDirectory: (Server, ServerLink, Boolean) -> Unit
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()

    BackHandler(navigator.canNavigateBack()) { navigator.navigateBack() }

    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium
                )
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        items = serverLinkList,
                        itemContent = { serverLink ->
                            if (serverLink.linkType == ServerLinkType.NAVIGATION) {
                                NavigationLinkView(serverLink, onLoadLink = { directory ->
                                    onLoadDirectory(server, serverLink, false)
                                })
                            } else {
                                PublicationLinkView(serverLink, onLoadLink = { directory ->
                                    onLoadDirectory(server, serverLink, false)
                                })
                            }
                        }
                    )
                }
            }
        },
        detailPane = {
            Logger.d(TAG, "Showing the detail pane: ${navigator.currentDestination}")
        }
    )
}

@Composable
@Preview
fun BrowseServerPreview() {
    VariantTheme {
        BrowseServerView(
            SERVER_LIST.get(0),
            SERVER_LIST.get(0).name,
            SERVER_LINK_LIST,
            onLoadDirectory = { _, _, _ -> })
    }
}