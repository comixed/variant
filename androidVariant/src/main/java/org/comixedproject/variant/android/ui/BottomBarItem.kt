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

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import org.comixedproject.variant.android.R

/**
 * <code>BottomBarItem</code> defines an item displayed on the bottom bat of the main application view.
 *
 * @author Darryl L. Pierce
 */
enum class BottomBarItem(val label: Int, val icon: ImageVector, val screen: NavigationScreen) {
    ServerList(R.string.serverButtonLabel, Icons.Filled.AccountBox, NavigationScreen.Servers),
    ComicList(
        R.string.comicsButtonLabel,
        Icons.AutoMirrored.Filled.List,
        NavigationScreen.ComicList
    ),
    Settings(R.string.settingsButtonLabel, Icons.Filled.Settings, NavigationScreen.Settings);

    companion object {
        val all = values()
    }
}