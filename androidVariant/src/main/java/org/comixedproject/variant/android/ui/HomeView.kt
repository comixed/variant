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

package org.comixedproject.variant.android.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.ui.server.BrowseServerView
import org.comixedproject.variant.android.ui.server.ServerManagementView
import org.comixedproject.variant.android.viewmodel.ServerLinkViewModel
import org.comixedproject.variant.android.viewmodel.ServerViewModel
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.model.server.ServerLink
import org.comixedproject.variant.shared.platform.Logger

private const val TAG = "HomeScreen"

/**
 * <code>HomeView</code> composes the primary view for the application.
 *
 * @author Darryl L. Pierce
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    serverList: List<Server>,
    serverViewModel: ServerViewModel,
    linkList: List<ServerLink>,
    serverLinkViewModel: ServerLinkViewModel,
    onSaveServer: (Long?, String, String, String, String) -> Unit
) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu",
                        modifier = Modifier.clickable { })
                },
                title = {
                    var title = stringResource(id = R.string.appName)
                    Text(title)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorScheme.primaryContainer,
                    titleContentColor = colorScheme.primary
                )
            )
        },
        bottomBar = {
            BottomBarView(currentDestination = navController.currentBackStackEntryAsState().value?.destination,
                onScreenChange = { route -> navController.navigate(route) })
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            NavHost(
                modifier = Modifier,
                navController = navController,
                startDestination = NavigationScreen.Servers.route
            ) {
                composable(
                    route = NavigationScreen.Servers.route
                ) {
                    ServerManagementView(
                        servers = serverList,
                        onSaveServer = onSaveServer,
                        onBrowseServer = { server ->
                            serverLinkViewModel.loadServerDirectory(server, server.url, false)
                            serverLinkViewModel.directory = server.url
                            navController.navigate("servers?serverId=${server.serverId}")
                        },
                        onDeleteServer = { }
                    )
                }
                composable(
                    route = NavigationScreen.BrowseServer.route,
                    arguments = NavigationScreen.BrowseServer.navArguments
                ) { entry ->
                    val serverId = entry.arguments?.getLong(NAVARG_SERVER_ID)
                    Logger.d(TAG, "serverId=${serverId} directory=${serverLinkViewModel.directory}")
                    val currentServer =
                        serverList.firstOrNull { server -> server.serverId == serverId }

                    if (currentServer != null) {
                        BrowseServerView(
                            server = currentServer,
                            serverLinks = linkList.filter { link -> link.serverId == currentServer.serverId }
                                .filter { link -> link.directory == serverLinkViewModel.directory }
                                .toList(),
                            serverLinkViewModel.directory,
                            onLoadDirectory = { target, selectedLink ->
                                serverLinkViewModel.loadServerDirectory(
                                    target,
                                    selectedLink.href,
                                    false
                                )
                                serverLinkViewModel.directory = selectedLink.href
                                navController.navigate("servers?serverId=${target.serverId}")
                            })
                    }
                }
                composable(route = NavigationScreen.ComicList.route) {
                    Text("Comic List!")
                }
                composable(route = NavigationScreen.Settings.route) {
                    Text("Settings!")
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeSPreview() {
    VariantTheme {
        HomeView(
            listOf(
                Server(null, "Server 1", "", "", ""),
                Server(null, "Server 2", "", "", ""),
                Server(null, "Server 3", "", "", ""),
                Server(null, "Server 4", "", "", ""),
                Server(null, "Server 5", "", "", "")
            ),
            ServerViewModel(),
            emptyList(),
            ServerLinkViewModel(),
            onSaveServer = { _, _, _, _, _ -> }
        )
    }
}