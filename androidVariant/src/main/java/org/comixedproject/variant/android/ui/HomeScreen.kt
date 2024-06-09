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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.ui.server.ServerManagementScreen
import org.comixedproject.variant.shared.model.VariantViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(viewModel: VariantViewModel = getViewModel()) {
    val selectedItem = remember { mutableStateOf(Screens.ComicManagement) }
    val navHost = rememberNavController()
    val navBackStackEntry by navHost.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            Screens.all.forEach { screen ->
                item(
                    selected = selectedItem.value == screen,
                    onClick = { selectedItem.value = screen },
                    label = { Text(stringResource(id = screen.label)) },
                    icon = {
                        Icon(
                            imageVector = screen.icon,
                            contentDescription = stringResource(id = screen.label)
                        )
                    },
                    alwaysShowLabel = true
                )
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Surface(modifier = Modifier.align(Alignment.Center)) {
                when (selectedItem.value) {
                    Screens.ServerManagement -> ServerManagementScreen(
                        viewModel.servers,
                        onSaveServer = { server ->
                            viewModel.saveServer(server)
                        },
                        onBrowserServer = {},
                        onDeleteServer = { server ->
                            viewModel.deleteServer(server)
                        })

                    Screens.ComicManagement -> Text("Comic Book Management")
                    Screens.Settings -> Text("Settings")
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