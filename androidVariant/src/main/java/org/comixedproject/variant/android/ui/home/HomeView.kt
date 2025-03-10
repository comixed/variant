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

package org.comixedproject.variant.android.ui.home

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.model.NavigationTarget
import org.comixedproject.variant.android.ui.comics.ComicView
import org.comixedproject.variant.android.ui.servers.ServersView
import org.comixedproject.variant.android.ui.setings.SettingsView

@Composable
fun HomeView() {
    var target by rememberSaveable { mutableStateOf(NavigationTarget.COMICS) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            NavigationTarget.entries.forEach { selection ->
                item(
                    icon = {
                        Icon(
                            selection.icon,
                            contentDescription = stringResource(selection.contentDescription)
                        )
                    },
                    label = { Text(stringResource(selection.label)) },
                    selected = target == selection,
                    onClick = { target = selection })
            }
        },
    ) {
        when (target) {
            NavigationTarget.SERVERS -> ServersView()
            NavigationTarget.COMICS -> ComicView()
            NavigationTarget.SETTINGS -> SettingsView()
        }
    }
}

@Composable
@Preview
fun HomePreview() {
    VariantTheme {
        HomeView()
    }
}