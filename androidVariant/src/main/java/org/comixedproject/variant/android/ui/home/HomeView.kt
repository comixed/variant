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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.launch
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.model.RootNavigationTargets
import org.comixedproject.variant.android.net.loadDirectory
import org.comixedproject.variant.android.ui.Screen
import org.comixedproject.variant.android.ui.comics.ComicsView
import org.comixedproject.variant.android.ui.getIconForScreen
import org.comixedproject.variant.android.ui.getLabelForScreen
import org.comixedproject.variant.android.ui.links.BrowseLinksView
import org.comixedproject.variant.android.ui.servers.ServersView
import org.comixedproject.variant.android.ui.setings.SettingsView
import org.comixedproject.variant.shared.platform.Logger
import org.comixedproject.variant.shared.viewmodel.ServerLinkViewModel
import org.comixedproject.variant.shared.viewmodel.ServerViewModel
import org.koin.androidx.compose.koinViewModel

private val TAG = "HomeView"

@Composable
fun HomeView(
    serverViewModel: ServerViewModel = koinViewModel(),
    serverLinkViewModel: ServerLinkViewModel = koinViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val serverList by serverViewModel.serverList.collectAsState()
    val serverLinkList by serverLinkViewModel.serverLinkList.collectAsState()

    var currentTarget by rememberSaveable { mutableStateOf(RootNavigationTargets.SERVERS) }
    var currentServerId by rememberSaveable { mutableStateOf<Long?>(null) }
    var currentDirectory by rememberSaveable { mutableStateOf("") }
    val navController = rememberNavController()
    var isLoading by rememberSaveable { mutableStateOf(false) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            RootNavigationTargets.entries.forEach { target ->
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
                ServersView(onBrowseServerLinks = { server ->
                    Logger.d(TAG, "Beginning to browse server: [${server.serverId}] ${server.name}")
                    currentServerId = server.serverId
                    currentDirectory = server.url
                    navController.navigate(
                        Screen.BrowseServerLinksScreen.withArgs(
                            "${server.serverId}",
                            "0"
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

            composable(
                route = "${Screen.BrowseServerLinksScreen.route}/{serverId}/{serverLinkId}",
                arguments = listOf(
                    navArgument("serverId") {
                        type = NavType.StringType
                        nullable = false
                    },
                    navArgument("serverLinkId") {
                        type = NavType.StringType
                        nullable = false
                    }
                )) { entry ->
                currentServerId?.let { serverId ->
                    val server = serverList.first { entry -> entry.serverId == serverId }
                    val directory = currentDirectory
                    val parentServerLink = serverLinkList
                        .filter { link -> link.serverId == server.serverId }
                        .filter { link -> link.downloadLink == directory }
                        .firstOrNull()
                    val title = when (parentServerLink) {
                        null -> server.name
                        else -> parentServerLink.title
                    }

                    Logger.d(TAG, "Showing directory: server=${server.name} directory=${directory}")

                    BrowseLinksView(
                        server,
                        directory,
                        parentServerLink?.directory,
                        isLoading,
                        title,
                        serverLinkList
                            .filter { link -> link.serverId == server.serverId }
                            .filter { link -> link.directory == directory },
                        onLoadDirectory = { server, directory, reload ->
                            Logger.d(
                                TAG,
                                "Loading directory on server: server=${server.name} directory=${directory} reload=${reload}"
                            )
                            isLoading = true

                            if (reload || !serverLinkViewModel.hasLinks(server, directory)) {
                                coroutineScope.launch {
                                    loadDirectory(
                                        serverLinkViewModel,
                                        server,
                                        directory,
                                        onSuccess = {
                                            currentServerId = server.serverId
                                            currentDirectory = directory
                                            isLoading = false
                                            navController.navigate(
                                                Screen.BrowseServerLinksScreen.withArgs(
                                                    "${server.serverId}",
                                                    "0"
                                                )
                                            )
                                        },
                                        onFailure = {
                                            isLoading = false
                                        })
                                }
                            } else {
                                currentServerId = server.serverId
                                currentDirectory = directory
                                isLoading = false
                                navController.navigate(
                                    Screen.BrowseServerLinksScreen.withArgs(
                                        "${server.serverId}",
                                        "0"
                                    )
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun HomePreview() {
    VariantTheme {
        HomeView()
    }
}