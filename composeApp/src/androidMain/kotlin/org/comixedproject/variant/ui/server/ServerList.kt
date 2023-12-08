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

package org.comixedproject.variant.ui.server

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.VariantTheme
import org.comixedproject.variant.model.Server
import org.comixedproject.variant.ui.bottomNavigationItems

/**
 * <code>ServerList</code> displays the list of servers. It displays a button to add a new entry.
 *
 * @author Darryl L. Pierce
 */
@Composable
fun ServerList(
    serverList: List<Server>, onAdd: () -> Unit
) {
    val selectedIndex = remember { mutableIntStateOf(0) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd,
                content = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add a new server"
                    )
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.primary) {
                bottomNavigationItems.forEachIndexed { index, bottomNavigationItem ->
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            selectedTextColor = Color.White,
                            unselectedIconColor = Color.Black,
                            unselectedTextColor = Color.Black,
                            indicatorColor = MaterialTheme.colorScheme.primary,
                        ),
                        label = {
                            Text(
                                stringResource(id = bottomNavigationItem.label),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        icon = {
                            Icon(
                                bottomNavigationItem.icon,
                                contentDescription = stringResource(id = bottomNavigationItem.label)
                            )
                        },
                        selected = (selectedIndex.intValue == index),
                        onClick = { selectedIndex.intValue = index })
                }
            }
        }
    ) {
        LazyColumn {
            items(serverList) { entry ->
                ServerListEntry(entry = entry, onClick = {})
            }
        }
    }
}

@Preview
@Composable
fun ServerListScreenAndroidPreview() {
    val serverList: List<Server> = listOf(
        Server(
            "",
            "Server 1",
            "http://comixedproject.org:7171/opds",
            "username",
            "password"
        ),
        Server(
            "",
            "Server 2",
            "http://comixedproject.org:7171/opds",
            "username",
            "password"
        ),
    )
    VariantTheme {
        ServerList(serverList, onAdd = {})
    }
}