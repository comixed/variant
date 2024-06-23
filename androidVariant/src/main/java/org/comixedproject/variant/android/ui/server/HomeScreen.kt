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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.ui.NavigationScreen
import org.comixedproject.variant.shared.model.VariantViewModel
import org.comixedproject.variant.shared.platform.Logger
import org.koin.androidx.compose.getViewModel

private const val TAG = "HomeScreen"

@Composable
fun HomeScreen(viewModel: VariantViewModel = getViewModel()) {
    val showBottomBar = remember { mutableStateOf(true) }
    val navController = rememberNavController()

    Scaffold(
        topBar = { Text("Top Bar") },
        bottomBar = {
            if (showBottomBar.value) {
                BottomBar(currentDestination = navController.currentBackStackEntryAsState().value?.destination,
                    onScreenChange = { route -> navController.navigate(route) })
            }
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
                    ServerManagementScreen(
                        servers = viewModel.servers,
                        onSaveServer = { server -> viewModel.saveServer(server) },
                        onBrowseServer = { server ->
                            navController.navigate("servers?serverId=${server.id}&linkId=")
                        },
                        onDeleteServer = { }
                    )
                }
                composable(
                    route = NavigationScreen.BrowserServer.route,
                    arguments = NavigationScreen.BrowserServer.navArguments
                ) { entry ->
                    val serverId = entry.arguments?.getString("serverId")
                    val linkId = entry.arguments?.getString("linkId").orEmpty()
                    Logger.d(TAG, "serverId=${serverId} linkId=${linkId}")
                    viewModel.servers.find { server -> server.id == serverId }?.let { server ->
                        var directory = ""
                        if (linkId.length > 0) {
                            viewModel.allLinks.find { link -> link.id == linkId }?.let { link ->
                                directory = link.link
                            }
                        }
                        viewModel.loadServerFeed(server, directory)
                        ServerBrowse(
                            server = server,
                            links = viewModel.links,
                            directory,
                            onLoadDirectory = { server, selectedLink ->
                                viewModel.loadServerFeed(server, selectedLink.link)
                                navController.navigate("servers?serverId=${server.id}&linkId=${selectedLink.id}")
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
fun HomeScreenPreview() {
    VariantTheme {
        HomeScreen()
    }
}