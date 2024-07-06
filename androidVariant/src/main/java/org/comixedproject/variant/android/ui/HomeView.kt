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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.ui.links.ServerLinkDetailView
import org.comixedproject.variant.android.ui.server.BrowseServerView
import org.comixedproject.variant.android.ui.server.ServerManagementView
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
    linkList: List<ServerLink>,
    onSaveServer: (Long?, String, String, String, String) -> Unit,
    onServerLoadDirectory: (Server, String, Boolean) -> Unit,
    onDownloadLink: (Server, ServerLink) -> Unit
) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Menu",
                        modifier = Modifier.clickable { },
                    )
                },
                title = {
                    var title = stringResource(id = R.string.appName)
                    Text(title)
                },
                colors =
                TopAppBarDefaults.topAppBarColors(
                    containerColor = colorScheme.primaryContainer,
                    titleContentColor = colorScheme.primary,
                ),
            )
        },
        bottomBar = {
            BottomBarView(
                currentDestination = navController.currentBackStackEntryAsState().value?.destination,
                onScreenChange = { route -> navController.navigate(route) },
            )
        },
    ) { padding ->
        Column(
            modifier =
            Modifier
                .padding(padding)
                .fillMaxSize(),
        ) {
            NavHost(
                modifier = Modifier,
                navController = navController,
                startDestination = NavigationScreen.Servers.route,
            ) {
                composable(
                    route = NavigationScreen.Servers.route,
                ) {
                    ServerManagementView(
                        servers = serverList,
                        onSaveServer = onSaveServer,
                        onBrowseServer = { server ->
                            onServerLoadDirectory(server, server.url, false)
                            val serverId = server.serverId
                            val serverLinkId = 0L
                            navController.navigate("${NavigationScreen.BrowseServer.route}/${serverId}/${serverLinkId}")
                        },
                        onDeleteServer = { },
                    )
                }
                composable(
                    route = "${NavigationScreen.BrowseServer.route}/{serverId}/{serverLinkId}",
                    arguments = listOf(
                        navArgument("serverId", { type = NavType.LongType }),
                        navArgument("serverLinkId", { type = NavType.LongType })
                    ),
                ) { entry ->
                    val serverId = entry.arguments?.getLong("serverId")
                    val serverLinkId = entry.arguments?.getLong("serverLinkId")
                    Logger.d(TAG, "serverId=$serverId serverLinkId=${serverLinkId}")
                    val currentServer =
                        serverList.firstOrNull { server -> server.serverId == serverId }
                    val currentParent = when (serverLinkId) {
                        0L -> currentServer?.url
                        else -> linkList.firstOrNull { link -> link.serverLinkId == serverLinkId }?.downloadLink
                    }

                    if (currentServer != null) {
                        BrowseServerView(
                            server = currentServer,
                            serverLinks =
                            linkList
                                .filter { link -> link.serverId == currentServer.serverId }
                                .filter { link -> link.directory == currentParent }
                                .toList(),
                            onLoadDirectory = { target, selectedLink ->
                                onServerLoadDirectory(target, selectedLink.downloadLink, false)
                                navController.navigate("${NavigationScreen.BrowseServer.route}/${target.serverId}/${selectedLink.serverLinkId}")
                            },
                            onShowLinkDetails = { target, selectedLink ->
                                navController.navigate("${NavigationScreen.ItemDetail.route}/${target.serverId}/${selectedLink.serverLinkId}")
                            }
                        )
                    }
                }
                composable(
                    route = "${NavigationScreen.ItemDetail.route}/{serverId}/{serverLinkId}",
                    arguments = listOf(
                        navArgument("serverId", { type = NavType.LongType }),
                        navArgument("serverLinkId", { type = NavType.LongType })
                    ),
                ) { entry ->
                    val serverId = entry.arguments?.getLong("serverId")
                    val serverLinkId = entry.arguments?.getLong("serverLinkId")
                    Logger.d(TAG, "serverId=$serverId serverLinkId=${serverLinkId}")
                    val server =
                        serverList.firstOrNull { server -> server.serverId == serverId }
                    val targetLink =
                        linkList.firstOrNull { link -> link.serverLinkId == serverLinkId }

                    if (server != null) {
                        ServerLinkDetailView(
                            server,
                            title = targetLink?.title ?: "",
                            coverUrl = targetLink?.coverUrl ?: "",
                            showDownload = true,
                            onDownloadEntry = { onDownloadLink(server, targetLink!!) },
                            onOpenEntry = { })
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
fun HomePreview() {
    VariantTheme {
        HomeView(
            SERVER_LIST,
            emptyList(),
            onSaveServer = { _, _, _, _, _ -> },
            onServerLoadDirectory = { _, _, _ -> },
            onDownloadLink = { _, _ -> }
        )
    }
}
