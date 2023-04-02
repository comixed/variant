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

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.prestige.android.R
import org.comixedproject.prestige.android.state.HomeRoute
import org.comixedproject.prestige.android.state.PrestigeDestination
import org.comixedproject.prestige.android.state.ServerListRoute

@Composable
fun PrestigeBottomNavigation(
    onScreenSelected: (PrestigeDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    BottomNavigation(modifier) {
        BottomNavigationItem(
            icon = {
                Icon(imageVector = Icons.Default.Home, contentDescription = null)
            },
            label = { Text(stringResource(R.string.navigation_label_home)) },
            selected = false,
            onClick = { onScreenSelected(HomeRoute) }
        )
        BottomNavigationItem(
            icon = {
                Icon(imageVector = Icons.Default.LibraryBooks, contentDescription = null)
            },
            label = { Text(stringResource(R.string.navigation_label_servers)) },
            selected = false,
            onClick = { onScreenSelected(ServerListRoute) }
        )
    }
}

@Preview
@Composable
fun PrestigeBottomNavigationPreview() {
    PrestigeBottomNavigation(
        onScreenSelected = {})
}