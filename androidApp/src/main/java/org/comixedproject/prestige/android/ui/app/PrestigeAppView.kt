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

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.comixedproject.prestige.android.PrestigeTheme
import org.comixedproject.prestige.android.state.HomeRoute
import org.comixedproject.prestige.android.state.ServerListRoute
import org.comixedproject.prestige.android.ui.LibraryListView

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        launchSingleTop = true
        restoreState = true
    }

@Composable
fun PrestigeAppView(modifier: Modifier = Modifier) {
    PrestigeTheme {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = {
                PrestigeBottomNavigation(onScreenSelected = { screen ->
                    navController.navigateSingleTopTo(
                        screen.route
                    )
                })
            }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = HomeRoute.route,
                modifier = Modifier.padding(padding)
            ) {
                composable(route = HomeRoute.route) { HomeView() }
                composable(route = ServerListRoute.route) { LibraryListView() }
            }
        }
    }
}

@Preview
@Composable
fun PrestigeAppViewPreview() {
    PrestigeAppView()
}