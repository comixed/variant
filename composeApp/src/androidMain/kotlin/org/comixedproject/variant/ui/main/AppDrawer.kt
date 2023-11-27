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

package org.comixedproject.variant.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.R
import org.comixedproject.variant.VariantTheme
import org.comixedproject.variant.ui.Screen

@Composable
fun AppDrawer(currentScreen: Screen, onScreenSelected: (Screen) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        AppDrawerHeader()

        Divider(color = MaterialTheme.colors.onSurface.copy(alpha = .2f))

        ScreenNavigationButton(
            icon = Icons.Filled.List,
            label = stringResource(id = R.string.server_list),
            isSelected = currentScreen == Screen.ServerList,
            onClick = {
                onScreenSelected.invoke(Screen.ServerList)
            })
        ScreenNavigationButton(
            icon = Icons.Filled.LibraryBooks,
            label = stringResource(id = R.string.comic_book_list),
            isSelected = currentScreen == Screen.ServerList,
            onClick = {
                onScreenSelected.invoke(Screen.ServerList)
            })
    }
}

@Preview
@Composable
fun AppDrawerPreview() {
    VariantTheme {
        AppDrawer(currentScreen = Screen.ServerList, onScreenSelected = {})
    }
}