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

package org.comixedproject.variant.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.comixedproject.variant.android.view.HomeView
import org.comixedproject.variant.platform.Log
import org.comixedproject.variant.viewmodel.VariantViewModel
import org.koin.androidx.compose.koinViewModel

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val variantViewModel: VariantViewModel = koinViewModel()
            val directory = applicationContext.filesDir.path

            variantViewModel.setLibraryDirectory(directory)

            VariantTheme {
                val comicBook by variantViewModel.comicBook.collectAsState()
                val comicBookList by variantViewModel.comicBookList.collectAsState()
                val loading by variantViewModel.loading.collectAsState()
                val browsingState by variantViewModel.browsingState.collectAsState()
                val selectionMode by variantViewModel.selectionMode.collectAsState()
                val selectionList by variantViewModel.selectionList.collectAsState()

                val coroutineScope = rememberCoroutineScope()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeView(
                        comicBook,
                        comicBookList,
                        browsingState,
                        loading,
                        selectionMode,
                        selectionList,
                        variantViewModel.address,
                        variantViewModel.username,
                        variantViewModel.password,
                        onLoadDirectory = { path, reload ->
                            coroutineScope.launch(Dispatchers.IO) {
                                Log.debug(TAG, "Loading directory: ${path} reload=${reload}")
                                variantViewModel.loadDirectory(path, reload)
                            }
                        },
                        onDownloadFile = { path, filename ->
                            coroutineScope.launch(Dispatchers.IO) {
                                Log.debug(TAG, "Downloading file: ${path} filename=${filename}")
                                variantViewModel.downloadFile(path, filename)
                            }
                        },
                        onReadComicBook = { comicBook ->
                            variantViewModel.readComicBook(comicBook)
                        },
                        onSetSelectionMode = { enabled ->
                            variantViewModel.setSelectMode(enabled)
                        },
                        onUpdateSelection = { comicBook ->
                            variantViewModel.updateSelectionList(comicBook.path)
                        },
                        onDeleteSelections = {
                            coroutineScope.launch(Dispatchers.Unconfined) {
                                variantViewModel.deleteSelections()
                            }
                        },
                        onSaveSettings = { address, username, password ->
                            variantViewModel.address = address
                            variantViewModel.username = username
                            variantViewModel.password = password
                        }
                    )
                }
            }
        }
    }
}
