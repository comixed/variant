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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.model.NavigationTarget
import org.comixedproject.variant.android.model.SERVER_LINK_LIST
import org.comixedproject.variant.android.model.SERVER_LIST
import org.comixedproject.variant.android.ui.Screen
import org.comixedproject.variant.android.ui.comics.ComicsView
import org.comixedproject.variant.android.ui.getIconForScreen
import org.comixedproject.variant.android.ui.getLabelForScreen
import org.comixedproject.variant.android.ui.servers.BrowseServerView
import org.comixedproject.variant.android.ui.servers.ServerEditView
import org.comixedproject.variant.android.ui.servers.ServerListView
import org.comixedproject.variant.android.ui.setings.SettingsView
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.model.server.ServerLink
import org.comixedproject.variant.shared.platform.Logger

private val TAG = "HomeView"

@Composable
fun HomeView(
    serverList: List<Server>,
    serverLinkList: List<ServerLink>,
    onSaveServer: (Server) -> Unit,
    onDeleteServer: (Server) -> Unit,
    onLoadServerContents: (Server, String, Boolean) -> Unit
) {
    var currentTarget by rememberSaveable { mutableStateOf(NavigationTarget.SERVERS) }
    val navController = rememberNavController()

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            NavigationTarget.entries.forEach { target ->
                Logger.d(TAG, "target=${target}")
                item(
                    icon = {
                        Icon(
                            imageVector = getIconForScreen(target.screen),
                            contentDescription = stringResource(getLabelForScreen(target.screen))
                        )
                    },
                    label = { Text(stringResource(getLabelForScreen(target.screen))) },
                    selected = currentTarget == target,
                    onClick = {
                        currentTarget = target
                        navController.navigate(target.screen.route)
                    })
            }
        },
    ) {
        NavHost(navController = navController, startDestination = Screen.ServersScreen.route) {
            composable(route = Screen.ServersScreen.route) {
                ServerListView(
                    serverList,
                    onAddServer = {
                        Logger.d(TAG, "Add a new server")
                        navController.navigate(Screen.AddServerScreen.route)
                    },
                    onEditServer = { server ->
                        Logger.d(TAG, "Editing server: name=${server.name}")
                        navController.navigate(Screen.EditServerScreen.withArgs("${server.serverId}"))
                    },
                    onDeleteServer = { server ->
                        Logger.d(TAG, "Deleting server: name=${server.name}")
                        navController.navigate(Screen.DeleteServerScreen.withArgs("${server.serverId}"))
                    },
                    onBrowseServer = { server ->
                        Logger.d(TAG, "Starting to browse server: name=${server.name}")
                        onLoadServerContents(server, server.url, false)
                        navController.navigate(
                            Screen.BrowseServerScreen.withArgs(
                                "${server.serverId}",
                                server.name,
                                server.url
                            )
                        )
                    })
            }

            composable(route = Screen.ComicsScreen.route) {
                ComicsView()
            }

            composable(route = Screen.SettingsScreen.route) {
                SettingsView()
            }

            composable(route = Screen.AddServerScreen.route) {
                ServerEditView(
                    Server(null, "", "", "", ""),
                    onSave = { server ->
                        Logger.d(TAG, "Saving new server: name=${server.name} url=${server.url}")
                        onSaveServer(server)
                        navController.navigate(Screen.ServersScreen.route)
                    },
                    onCancel = {
                        Logger.d(TAG, "Cancelling new server")
                        navController.navigate(Screen.ServersScreen.route)
                    })
            }

            composable(
                route = "${Screen.EditServerScreen.route}/{serverId}",
                arguments = listOf(
                    navArgument("serverId") {
                        type = NavType.StringType
                        nullable = false
                    }
                )) { entry ->
                val serverId = entry.arguments?.getString("serverId")
                val server = serverList.filter { it.serverId == serverId!!.toLong() }.first()

                ServerEditView(
                    server!!,
                    onSave = { server ->
                        Logger.d(
                            TAG,
                            "Saving server changes: id=${server.serverId} name=${server.name} url=${server.url}"
                        )
                        onSaveServer(server)
                        navController.navigate(Screen.ServersScreen.route)
                    },
                    onCancel = {
                        Logger.d(TAG, "Canceling changes to server")
                        navController.navigate(Screen.ServersScreen.route)
                    })
            }

            composable(
                route = "${Screen.DeleteServerScreen.route}/{serverId}",
                arguments = listOf(
                    navArgument("serverId") {
                        type = NavType.StringType
                        nullable = false
                    }
                )) { entry ->
                val serverId = entry.arguments?.getString("serverId")
                val server = serverList.filter { it.serverId == serverId!!.toLong() }.first()

                Text("Delete Server Screen!")
            }

            composable(
                route = "${Screen.BrowseServerScreen.route}/{serverId}/{title}/{directory}",
                arguments = listOf(
                    navArgument("serverId") {
                        type = NavType.StringType
                        nullable = false
                    },
                    navArgument("title") {
                        type = NavType.StringType
                        nullable = false
                    },
                    navArgument("directory") {
                        type = NavType.StringType
                        nullable = false
                    }
                )) { entry ->
                val serverId = entry.arguments?.getString("serverId")
                val title = entry.arguments?.getString("title")
                val directory = entry.arguments?.getString("directory")
                val server = serverList.filter { it.serverId == serverId!!.toLong() }.first()

                Logger.d(TAG, "Showing directory: server=${server.name} directory=${directory}")

                BrowseServerView(
                    server!!,
                    title!!,
                    serverLinkList,
                    onLoadDirectory = { server, serverLink, reload ->
                        Logger.d(
                            TAG,
                            "Loading directory on server: server=${server.name} directory=${directory} reload=${reload}"
                        )
                        onLoadServerContents(server, serverLink.downloadLink, reload)
                        navController.navigate(
                            Screen.BrowseServerScreen.withArgs(
                                "${server.serverId}",
                                serverLink.title,
                                serverLink.downloadLink
                            )
                        )
                    }
                )
            }
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
            onLoadServerContents = { _, _, _ -> })
    }
}