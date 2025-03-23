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

package org.comixedproject.variant.android.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.ui.graphics.vector.ImageVector
import org.comixedproject.variant.android.R

sealed class Screen(val route: String) {
    object ServersScreen : Screen("servers_screen")
    object ComicsScreen : Screen("comics_screen")
    object SettingsScreen : Screen("settings_screen")
}

fun getLabelForScreen(target: Screen): Int {
    return when (target) {
        Screen.ComicsScreen -> R.string.comicsButtonLabel
        Screen.ServersScreen -> R.string.serverButtonLabel
        Screen.SettingsScreen -> R.string.settingsButtonLabel
        else -> R.string.unknownLabel
    }
}

fun getIconForScreen(target: Screen): ImageVector {
    return when (target) {
        Screen.ComicsScreen -> Icons.Rounded.Home
        Screen.ServersScreen -> Icons.Rounded.AccountCircle
        Screen.SettingsScreen -> Icons.Rounded.Settings
        else -> Icons.Rounded.Warning
    }
}