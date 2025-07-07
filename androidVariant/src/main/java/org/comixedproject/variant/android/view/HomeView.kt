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

package org.comixedproject.variant.android.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.view.comics.ComicBookView
import org.comixedproject.variant.android.view.reading.ReadingView
import org.comixedproject.variant.android.view.server.ServerView
import org.comixedproject.variant.android.view.settings.SettingsView
import org.comixedproject.variant.viewmodel.VariantViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeView() {
    val variantViewModel: VariantViewModel = koinViewModel()
    var currentDestination by remember { mutableStateOf(AppDestination.COMICS) }
    val coroutineScope = rememberCoroutineScope()
    val comicBook by variantViewModel.comicBook.collectAsState()

    Scaffold(
        topBar = {
            VariantTopAppBar(
                onBrowseComics = { currentDestination = AppDestination.COMICS },
                onBrowseServer = {
                    coroutineScope.launch(Dispatchers.IO) {
                        variantViewModel.loadDirectory(
                            variantViewModel.browsingState.value.currentPath,
                            false
                        )
                    }
                    currentDestination = AppDestination.BROWSE
                },
                onUpdateSettings = { currentDestination = AppDestination.SETTINGS })
        },
        content = { padding ->
            when (currentDestination) {
                AppDestination.COMICS ->
                    if (comicBook != null) {
                        ReadingView(
                            comicBook!!,
                            modifier = Modifier.padding(padding),
                            onStopReading = { variantViewModel.readComicBook(null) }
                        )
                    } else {
                        ComicBookView(
                            onReadComicBook = { comicBook ->
                                variantViewModel.readComicBook(comicBook)
                            },
                            modifier = Modifier.padding(padding)
                        )
                    }

                AppDestination.BROWSE -> ServerView(modifier = Modifier.padding(padding))
                AppDestination.SETTINGS -> SettingsView(onCloseSettings = {
                    currentDestination = AppDestination.COMICS
                }, modifier = Modifier.padding(padding))
            }
        }
    )
}

@Composable
@Preview
fun HomeViewPreview() {
    VariantTheme { HomeView() }
}