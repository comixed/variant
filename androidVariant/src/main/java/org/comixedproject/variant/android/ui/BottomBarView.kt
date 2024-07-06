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

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import org.comixedproject.variant.android.VariantTheme

/**
 * <code>BottomBarView</code> composes the view of items displayed on the main application's bottom bar.
 *
 * @author Darryl L. Pierce
 */
@Composable
fun BottomBarView(
    currentDestination: NavDestination?,
    onScreenChange: (route: String) -> Unit
) {
    var selectedItem by remember { mutableIntStateOf(0) }

    NavigationBar {
        BottomBarItem.all.forEachIndexed { index, item ->
            NavigationBarItem(selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    onScreenChange(item.screen.route)
                },
                label = { Text(text = stringResource(id = item.label)) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = stringResource(id = item.label)
                    )
                })
        }
    }
}

@Preview
@Composable
fun BottomBarPreview() {
    VariantTheme {
        BottomBarView(null, onScreenChange = {})
    }
}