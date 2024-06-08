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
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.model.server.Server


@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ServerManagementScreen() {
    val navigator = rememberListDetailPaneScaffoldNavigator<Server>()

    BackHandler {
        navigator.navigateBack()
    }

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            ServerList(listOf(
                Server(
                    id = 1,
                    name = "My Server",
                    url = "http://www.comixedproject.org:7171/opds",
                    username = "reader@comixedproject.org"
                ), Server(
                    id = 2,
                    name = "My Server",
                    url = "http://www.comixedproject.org:7171/opds",
                    username = "reader@comixedproject.org"
                ), Server(
                    id = 3,
                    name = "My Server",
                    url = "http://www.comixedproject.org:7171/opds",
                    username = "reader@comixedproject.org"
                ), Server(
                    id = 4,
                    name = "My Server",
                    url = "http://www.comixedproject.org:7171/opds",
                    username = "reader@comixedproject.org"
                ), Server(
                    id = 5,
                    name = "My Server",
                    url = "http://www.comixedproject.org:7171/opds",
                    username = "reader@comixedproject.org"
                )
            ),
                onServerSelect = { server ->
                    navigator.navigateTo(
                        ListDetailPaneScaffoldRole.Detail,
                        server
                    )
                }
            )
        },
        detailPane = {
            navigator.currentDestination?.content?.let { server ->
                Text(server.name)
            }
        })
}

@Preview
@Composable
fun ServerManagementScreenPreview() {
    VariantTheme {
        ServerManagementScreen()
    }
}