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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.model.NavigationTarget
import org.comixedproject.variant.android.ui.Screen
import org.comixedproject.variant.android.ui.comics.ComicsView
import org.comixedproject.variant.android.ui.getIconForScreen
import org.comixedproject.variant.android.ui.getLabelForScreen
import org.comixedproject.variant.android.ui.servers.ServersView
import org.comixedproject.variant.android.ui.setings.SettingsView
import org.comixedproject.variant.shared.platform.Log
import org.comixedproject.variant.shared.viewmodel.ComicBookViewModel
import org.comixedproject.variant.shared.viewmodel.ServerLinkViewModel
import org.comixedproject.variant.shared.viewmodel.ServerViewModel
import org.koin.androidx.compose.koinViewModel

private val TAG = "HomeView"

@Composable
fun HomeView(
    serverViewModel: ServerViewModel = koinViewModel(),
    serverLinkViewModel: ServerLinkViewModel = koinViewModel(),
    comicBookViewModel: ComicBookViewModel = koinViewModel()
) {
    var currentTarget by rememberSaveable { mutableStateOf(NavigationTarget.SERVERS) }
    val navController = rememberNavController()

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            NavigationTarget.entries.forEach { target ->
                Log.debug(TAG, "target=${target}")
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
                ServersView(serverViewModel, serverLinkViewModel)
            }

            composable(route = Screen.ComicsScreen.route) {
                ComicsView(comicBookViewModel)
            }

            composable(route = Screen.SettingsScreen.route) {
                SettingsView()
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