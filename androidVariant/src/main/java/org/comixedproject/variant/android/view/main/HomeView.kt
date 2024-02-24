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

package org.comixedproject.variant.android.view.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.view.opds.ServerNavigator
import org.comixedproject.variant.state.ServerListViewModel
import org.koin.androidx.compose.getViewModel

val TAG_TITLE_TEXT = "tag.title-text"
val TAG_BOTTOM_BAR = "tag.bottom-bar"
val TAG_SERVER_NAVIGATOR = "tag.server-navigator"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView() {
    val serverListViewModel: ServerListViewModel = getViewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text(
                        modifier = Modifier.testTag(TAG_TITLE_TEXT),
                        text = stringResource(id = R.string.app_title_text)
                    )
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.testTag(TAG_BOTTOM_BAR),
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                Text("Bottom Bar Stuff")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ServerNavigator(modifier = Modifier.testTag(TAG_SERVER_NAVIGATOR))
        }
    }
}

@Preview
@Composable
fun HomeViewPreview() {
    VariantTheme {
        HomeView()
    }
}