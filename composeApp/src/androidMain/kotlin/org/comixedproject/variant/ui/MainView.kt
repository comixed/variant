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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.comixedproject.variant.EmptyComposable
import org.comixedproject.variant.VariantTheme
import org.comixedproject.variant.model.OPDSServerEntry
import org.comixedproject.variant.topBarFun

@Composable
fun MainView(actionBarFun: topBarFun = { EmptyComposable() }) {
    val showAddDialog = remember { mutableStateOf(false) }
    val opdsServerList = remember { SnapshotStateList<OPDSServerEntry>() }
    val selectedIndex = remember { mutableIntStateOf(0) }

    opdsServerList.add(
        OPDSServerEntry(
            "Server 1",
            "http://www.comixedproject.org:7171/opds",
            "admin@comixedproject.org",
            "c0m1x3d!4dm1n",
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        )
    )

    VariantTheme {
        Scaffold(
            topBar = {
                actionBarFun(selectedIndex.value)
            },
            floatingActionButton = {
                if (selectedIndex.value == 0) {
                    FloatingActionButton(modifier = Modifier.padding(16.dp),
                        shape = FloatingActionButtonDefaults.largeShape,
                        containerColor = MaterialTheme.colorScheme.secondary,
                        onClick = { showAddDialog.value = true }
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Server")
                    }
                }
            },
            bottomBar = {
                NavigationBar(containerColor = MaterialTheme.colorScheme.primary) {
                    bottomNavigationItems.forEachIndexed { index, bottomItem ->
                        NavigationBarItem(
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.White,
                                selectedTextColor = Color.White,
                                unselectedIconColor = Color.Black,
                                unselectedTextColor = Color.Black,
                                indicatorColor = MaterialTheme.colorScheme.primary
                            ),
                            label = {
                                Text(bottomItem.route, style = MaterialTheme.typography.bodyMedium)
                            },
                            icon = {
                                Icon(
                                    bottomItem.icon,
                                    contentDescription = bottomItem.iconContentDescription
                                )
                            },
                            selected = selectedIndex.intValue == index,
                            onClick = {
                                selectedIndex.value = index
                            }
                        )
                    }
                }
            }
        ) { padding ->
            Box(Modifier.padding(padding)) {
                when (selectedIndex.intValue) {
                    0 -> OPDSServerListScreen(opdsServerList)
                    else -> throw Exception("No valid screen to display: selectedIndex={selectedIndex.intValue}")
                }
            }
        }
    }
}

@Preview
@Composable
fun MainViewAndroidPreview() {
    MainView()
}
