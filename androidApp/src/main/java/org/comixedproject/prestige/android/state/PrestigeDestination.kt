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

package org.comixedproject.prestige.android.state

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.ui.graphics.vector.ImageVector
import org.comixedproject.prestige.android.R

interface PrestigeDestination {
    val icon: ImageVector
    val route: String
    val label: Int
}

object HomeRoute : PrestigeDestination {
    override val icon = Icons.Filled.Home
    override val route = "home"
    override val label = R.string.navigation_label_home
}

object ServerListRoute : PrestigeDestination {
    override val icon = Icons.Filled.LibraryBooks
    override val route = "servers"
    override val label = R.string.navigation_label_servers
}