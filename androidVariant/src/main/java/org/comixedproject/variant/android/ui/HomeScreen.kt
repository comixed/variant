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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import org.comixedproject.variant.android.ui.server.BottomBar
import org.comixedproject.variant.android.ui.server.BrowseServerScreen
import org.comixedproject.variant.android.ui.server.ServerManagementScreen
import org.comixedproject.variant.shared.model.server.AcquisitionLink
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.platform.Logger

private const val TAG = "HomeScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    servers: List<Server>,
    displayLinks: List<AcquisitionLink>,
    allLinks: List<AcquisitionLink>,
    onSaveServer: (Server) -> Unit,
    onLoadDirectory: (Server, String, Boolean) -> Unit
) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text(stringResource(id = R.string.appNameAndVersion))
                }
            )
        },
        bottomBar = {
            BottomBar(currentDestination = navController.currentBackStackEntryAsState().value?.destination,
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
                    ServerManagementScreen(
                        servers = servers,
                        onSaveServer = { server -> onSaveServer(server) },
                        onBrowseServer = { server ->
                            navController.navigate("servers?serverId=${server.id}&linkId=")
                        },
                        onDeleteServer = { }
                    )
                }
                composable(
                    route = NavigationScreen.BrowseServer.route,
                    arguments = NavigationScreen.BrowseServer.navArguments
                ) { entry ->
                    val serverId = entry.arguments?.getString("serverId")
                    val linkId = entry.arguments?.getString("linkId").orEmpty()
                    Logger.d(TAG, "serverId=${serverId} linkId=${linkId}")
                    servers.find { server -> server.id == serverId }?.let { server ->
                        var directory = ""
                        if (linkId.isNotEmpty()) {
                            allLinks.find { link -> link.id == linkId }
                                ?.let { link ->
                                    directory = link.link
                                }
                        }

                        onLoadDirectory(server, directory, false)

                        BrowseServerScreen(
                            server = server,
                            acquisitionLinks = displayLinks,
                            directory,
                            onLoadDirectory = { target, selectedLink ->
                                navController.navigate("servers?serverId=${target.id}&linkId=${selectedLink.id}")
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
        HomeScreen(
            emptyList(),
            emptyList(),
            emptyList(),
            onSaveServer = { },
            onLoadDirectory = { _, _, _ -> }
        )
    }
}