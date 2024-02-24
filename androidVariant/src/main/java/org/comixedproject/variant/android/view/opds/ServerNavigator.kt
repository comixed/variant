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

package org.comixedproject.variant.android.view.opds

import androidx.activity.compose.BackHandler
import androidx.compose.material3.adaptive.AnimatedPane
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.model.BLANK_SERVER
import org.comixedproject.variant.model.Server
import org.comixedproject.variant.state.ServerListViewModel
import org.koin.androidx.compose.getViewModel

val TAG_SERVER_NAVIGATOR_SERVER_LIST = "tag.server-navigator.server-list"
val TAG_SERVER_NAVIGATOR_SERVER_DETAIL = "tag.server-navigator.server-detail"

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ServerNavigator(modifier: Modifier = Modifier) {
    var selectedServer: Server? by remember { mutableStateOf(null) }
    val navigator = rememberListDetailPaneScaffoldNavigator<Nothing>()

    val serverListViewModel: ServerListViewModel = getViewModel()

    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }

    ListDetailPaneScaffold(
        modifier = modifier,
        scaffoldState = navigator.scaffoldState,
        listPane = {
            AnimatedPane(Modifier) {
                ServerList(
                    serverList = serverListViewModel.serverList,
                    onSelect = { server ->
                        selectedServer = server
                        navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                    },
                    onCreate = {
                        navigator.navigateTo(ListDetailPaneScaffoldRole.Extra)
                    },
                    onDelete = { server ->
                        serverListViewModel.deleteServer(server)
                    },
                    modifier = Modifier.testTag(TAG_SERVER_NAVIGATOR_SERVER_LIST)
                )
            }
        },
        detailPane = {
            AnimatedPane(Modifier) {
                selectedServer?.let { server ->
                    ServerDetail(
                        server = server,
                        onEdit = { },
                        modifier = Modifier.testTag(TAG_SERVER_NAVIGATOR_SERVER_DETAIL)
                    )
                }
            }
        },
        extraPane = {
            AnimatedPane(Modifier) {
                ServerEdit(
                    BLANK_SERVER,
                    onSave = { server ->
                        serverListViewModel.createServer(server)
                        navigator.navigateBack()
                    })
            }
        })
}

@Preview
@Composable
fun ServerNavigatorPreview() {
    VariantTheme {
        ServerNavigator()
    }
}