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

package org.comixedproject.variant.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import org.comixedproject.variant.R

/**
 * <code>BottomItem</code> represents a single item displayed at the bottom of the application screen.
 *
 * @author Darryl L. Pierce
 */
data class BottomItem(
    val route: String,
    val icon: ImageVector,
    val label: Int
)

val bottomNavigationItems = listOf(
    BottomItem(
        Screen.ServerList.title, Icons.Filled.Home,
        R.string.server_list_label
    ),
    BottomItem(
        Screen.ComicBookList.title, Icons.Filled.LibraryBooks,
        R.string.comic_book_list_label
    ),
    BottomItem(
        Screen.Settings.title, Icons.Filled.Settings,
        R.string.settings_label
    )
)