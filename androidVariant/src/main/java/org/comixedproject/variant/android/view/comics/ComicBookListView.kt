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

package org.comixedproject.variant.android.view.comics

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.COMIC_BOOK_LIST
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.model.library.ComicBook
import org.comixedproject.variant.platform.Log

private val TAG = "ComicBookListView"

@Composable
fun ComicBookListView(comicBookList: List<ComicBook>, modifier: Modifier = Modifier) {
    Scaffold(
        content = { padding ->
            if (comicBookList.isEmpty()) {
                Log.debug(
                    TAG,
                    "No comics to display"
                )
                Text(stringResource(R.string.emptyComicListText))
            } else {
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize(),
                    contentPadding = padding
                ) {
                    items(comicBookList) { comicBook ->
                        Log.debug(
                            TAG,
                            "Showing comic book: ${comicBook.filename}"
                        )

                        ComicBookListItemView(comicBook, modifier = Modifier.padding(padding))
                    }
                }
            }
        }, modifier = modifier
    )
}

@Composable
@Preview
fun ComicBookListView_preview() {
    VariantTheme {
        ComicBookListView(COMIC_BOOK_LIST)
    }
}