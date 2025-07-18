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

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.COMIC_BOOK_LIST
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.view.comics.ComicBookView
import org.comixedproject.variant.android.view.reading.ReadingView
import org.comixedproject.variant.android.view.server.ServerView
import org.comixedproject.variant.android.view.settings.SettingsView
import org.comixedproject.variant.model.library.ComicBook
import org.comixedproject.variant.platform.Log
import org.comixedproject.variant.viewmodel.BrowsingState

private const val TAG = "HomeView"

@Composable
fun HomeView(
    comicBook: ComicBook?,
    comicBookList: List<ComicBook>,
    browsingState: BrowsingState,
    loading: Boolean,
    selectionMode: Boolean,
    selectionList: List<String>,
    address: String, username: String, password: String,
    onLoadDirectory: (String, Boolean) -> Unit,
    onDownloadFile: (String, String) -> Unit,
    onReadComicBook: (ComicBook?) -> Unit,
    onSetSelectionMode: (Boolean) -> Unit,
    onUpdateSelection: (ComicBook) -> Unit,
    onDeleteSelections: () -> Unit,
    onSaveSettings: (String, String, String) -> Unit
) {
    var currentDestination by remember { mutableStateOf(AppDestination.COMICS) }

    Scaffold(
        topBar = { VariantTopAppBar() },
        content = { padding ->
            NavigationSuiteScaffold(
                navigationSuiteItems = {
                    AppDestination.entries.forEach {
                        item(
                            icon = {
                                Icon(
                                    painterResource(it.icon),
                                    contentDescription = stringResource(it.label)
                                )
                            },
                            label = { Text(stringResource(it.label)) },
                            selected = it == currentDestination,
                            onClick = {
                                if (it == AppDestination.BROWSE
                                ) {
                                    onLoadDirectory(browsingState.currentPath, false)
                                }
                                currentDestination = it
                            }
                        )
                    }
                },
                modifier = Modifier.padding(padding)
            ) {
                when (currentDestination) {
                    AppDestination.COMICS ->
                        if (comicBook != null) {
                            ReadingView(
                                comicBook,
                                onStopReading = { onReadComicBook(null) },
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                        } else {
                            ComicBookView(
                                comicBookList,
                                selectionMode,
                                selectionList,
                                onSetSelectionMode = {
                                    Log.info(TAG, "Setting selection mode: ${it}")
                                    onSetSelectionMode(it)
                                },
                                onComicBookClicked = { comicBook ->
                                    if (selectionMode) {
                                        Log.info(
                                            TAG,
                                            "Toggling comic book selection: ${comicBook.path}"
                                        )
                                        onUpdateSelection(comicBook)
                                    } else {
                                        Log.info(TAG, "Reading comic book: ${comicBook.filename}")
                                        onReadComicBook(comicBook)
                                    }
                                },
                                onDeleteComics = { onDeleteSelections() },
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                        }

                    AppDestination.BROWSE -> ServerView(
                        browsingState, comicBookList, loading,
                        onLoadDirectory = { path, reload -> onLoadDirectory(path, reload) },
                        onDownloadFile = { path, filename -> onDownloadFile(path, filename) },
                        modifier = Modifier
                            .fillMaxSize()
                    )

                    AppDestination.SETTINGS -> SettingsView(
                        address, username, password,
                        onSaveSettings = { address, username, password ->
                            Log.info(
                                TAG,
                                "Updating server settings: address=${address} username=${username} password=${
                                    password.first()
                                }*****"
                            )
                            onSaveSettings(address, username, password)
                            currentDestination = AppDestination.COMICS
                        },
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
        })
}

@Composable
@Preview
fun HomeViewPreview() {
    VariantTheme {
        HomeView(
            COMIC_BOOK_LIST.get(0),
            COMIC_BOOK_LIST,
            BrowsingState("", "", "", listOf(), listOf()),
            false,
            false,
            listOf(),
            "http://www.comixedproject.org:7171", "reader@comixedproject.org", "my!password",
            onLoadDirectory = { _, _ -> },
            onDownloadFile = { _, _ -> },
            onReadComicBook = { _ -> },
            onSetSelectionMode = { _ -> },
            onUpdateSelection = { _ -> },
            onDeleteSelections = { }, onSaveSettings = { _, _, _ -> }
        )
    }
}