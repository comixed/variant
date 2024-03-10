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
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.model.BLANK_SERVER
import org.comixedproject.variant.model.Server

val TAG_SERVER_NAVIGATOR_SERVER_LIST = "tag.server-navigator.server-list"
val TAG_SERVER_NAVIGATOR_SERVER_DETAIL = "tag.server-navigator.server-detail"

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ServerNavigator(
    serverList: List<Server>,
    onSave: (Server) -> Unit,
    onDelete: (Server) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedServerId: String? by rememberSaveable { mutableStateOf(null) }
    val navigator = rememberListDetailPaneScaffoldNavigator<Nothing>()

    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }

    ListDetailPaneScaffold(
        modifier = modifier,
        scaffoldState = navigator.scaffoldState,
        listPane = {
            AnimatedPane(Modifier) {
                ServerList(
                    serverList = serverList,
                    onSelect = { server ->
                        selectedServerId = server.id
                        navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                    },
                    onCreate = {
                        selectedServerId = "[CREATE]"
                        navigator.navigateTo(ListDetailPaneScaffoldRole.Extra)
                    },
                    onDelete = { server -> onDelete(server) },
                    modifier = Modifier.testTag(TAG_SERVER_NAVIGATOR_SERVER_LIST)
                )
            }
        },
        detailPane = {
            AnimatedPane(Modifier) {
                selectedServerId?.let { server ->
                    ServerDetail(
                        server = serverList.first { it.id == selectedServerId },
                        onEdit = { server ->
                            selectedServerId = server.id
                            navigator.navigateTo(ListDetailPaneScaffoldRole.Extra)
                        },
                        modifier = Modifier.testTag(TAG_SERVER_NAVIGATOR_SERVER_DETAIL)
                    )
                }
            }
        },
        extraPane = {
            AnimatedPane(Modifier) {
                val server =
                    if (selectedServerId != "[CREATE]") serverList.first { it.id == selectedServerId } else BLANK_SERVER
                ServerEdit(
                    server,
                    onSave = { server ->
                        onSave(server)
                        navigator.navigateBack()
                    }
                )
            }
        })
}

@Preview
@Composable
fun ServerNavigatorPreview() {
    VariantTheme {
        ServerNavigator(listOf(
            Server(
                "1",
                "Server 1",
                "http://comixedproject.org:7171/opds",
                "admin@comixedproject.org",
                "my!password"
            ),
            Server(
                "2",
                "Server 2",
                "http://comixedproject.org:7171/opds",
                "admin@comixedproject.org",
                "my!password"
            ),
            Server(
                "3",
                "Server 3",
                "http://comixedproject.org:7171/opds",
                "admin@comixedproject.org",
                "my!password"
            ),
            Server(
                "4",
                "Server 4",
                "http://comixedproject.org:7171/opds",
                "admin@comixedproject.org",
                "my!password"
            ),
            Server(
                "5",
                "Server 5",
                "http://comixedproject.org:7171/opds",
                "admin@comixedproject.org",
                "my!password"
            ),
        ),
            onSave = {},
            onDelete = {})
    }
}