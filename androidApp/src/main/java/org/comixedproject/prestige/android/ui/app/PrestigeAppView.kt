/*
 * Prestige - A digital comic book reading application.
 * Copyright (C) 2023, The ComiXed Project
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

package org.comixedproject.prestige.android.ui.app

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.comixedproject.prestige.android.PrestigeTheme
import org.comixedproject.prestige.android.R
import org.comixedproject.prestige.android.state.AddServerRoute
import org.comixedproject.prestige.android.state.HomeRoute
import org.comixedproject.prestige.android.state.PrestigeAppViewModel
import org.comixedproject.prestige.android.state.ServerListRoute
import org.comixedproject.prestige.android.ui.LibraryListView
import org.comixedproject.prestige.android.ui.library.LibraryEditView
import org.comixedproject.prestige.android.ui.library.LibrarySaver
import org.comixedproject.prestige.model.library.Library

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        launchSingleTop = true
        restoreState = true
    }

@Composable
fun PrestigeAppView(
    modifier: Modifier = Modifier,
    appViewModel: PrestigeAppViewModel = viewModel()
) {
    val newLibrary: Library by rememberSaveable(stateSaver = LibrarySaver) { mutableStateOf(Library()) }

    PrestigeTheme {
        val navController = rememberNavController()
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.text_app_title)) }
                )
            },
            bottomBar = {
                PrestigeBottomNavigation(onScreenSelected = { screen ->
                    navController.navigateSingleTopTo(
                        screen.route
                    )
                })
            },
            modifier = Modifier.fillMaxWidth()
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = HomeRoute.route,
                modifier = Modifier.padding(padding)
            ) {
                composable(route = HomeRoute.route) { HomeView() }
                composable(route = ServerListRoute.route) {
                    LibraryListView(
                        libraries = appViewModel.libraryServers, onAddLibrary = {
                            navController.navigateSingleTopTo(AddServerRoute.route)
                        },
                        onRemoveLibrary = { library ->
                            appViewModel.removeLibrary(library)
                            navController.navigateSingleTopTo(ServerListRoute.route)
                        })
                }
                composable(route = AddServerRoute.route) {
                    LibraryEditView(
                        library = newLibrary,
                        onSave = { name, url, username, password ->
                            appViewModel.addLibrary(
                                Library(
                                    name = name,
                                    url = url,
                                    username = username,
                                    password = password
                                )
                            )
                            navController.navigateSingleTopTo(ServerListRoute.route)
                        },
                        onCancel = {
                            navController.navigateSingleTopTo(ServerListRoute.route)
                        })
                }
            }
        }
    }
}

@Preview
@Composable
fun PrestigeAppPreview() {
    PrestigeTheme {
        PrestigeAppView()
    }
}