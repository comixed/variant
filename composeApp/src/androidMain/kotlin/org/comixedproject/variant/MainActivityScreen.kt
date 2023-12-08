/*
 * Variant - A digital comic book reading application for iPad, Android, and desktops.
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

package org.comixedproject.variant

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.comixedproject.variant.model.serverTemplate
import org.comixedproject.variant.ui.Screen
import org.comixedproject.variant.ui.server.EditServer
import org.comixedproject.variant.ui.server.ServerList
import org.comixedproject.variant.viewmodel.MainViewModel
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivityScreen(
    navController: NavHostController,
    openNavigatorDrawer: () -> Unit
) {
    val mainViewModel: MainViewModel = getViewModel()

    NavHost(navController = navController, startDestination = Screen.ServerList.route) {
        composable(Screen.ServerList.route) {
            ServerList(
                mainViewModel.serverList,
                onAdd = { navController.navigate(Screen.ServerEdit.route) })
        }
        composable(Screen.ServerEdit.route) {
            EditServer(
                serverTemplate,
                onSave = { name, url, username, password ->
                    mainViewModel.createServer(
                        name,
                        url,
                        username,
                        password
                    )
                }, onCancel = { navController.navigate(Screen.ServerList.route) })
        }
    }
}