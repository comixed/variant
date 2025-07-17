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

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.COMIC_BOOK_LIST
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.model.library.ComicBook
import org.comixedproject.variant.platform.Log

private val TAG = "ComicBookView"

@Composable
fun ComicBookView(
    comicBookList: List<ComicBook>,
    selectionMode: Boolean,
    selectionList: List<String>,
    onSetSelectionMode: (Boolean) -> Unit,
    onComicBookClicked: (ComicBook) -> Unit,
    onDeleteComics: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        content = { padding ->
            ComicBookListView(
                comicBookList,
                selectionList,
                onClick = { onComicBookClicked(it) },
                modifier = modifier.padding(padding)
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    if (selectionMode) {
                        IconButton(onClick = { onSetSelectionMode(false) }) {
                            Icon(
                                painterResource(id = R.drawable.ic_selection_mode_on),
                                contentDescription = stringResource(R.string.markReadLabel)
                            )
                        }
                    } else {
                        IconButton(onClick = { onSetSelectionMode(true) }) {
                            Icon(
                                painterResource(id = R.drawable.ic_selection_mode_off),
                                contentDescription = stringResource(R.string.markReadLabel)
                            )
                        }
                    }
                    if (!selectionList.isEmpty()) {
                        IconButton(enabled = !selectionList.isEmpty(), onClick = {
                            Log.info(TAG, "Deleting ${selectionList.size} comic book(s)")
                            onDeleteComics()
                        }) {
                            Icon(
                                painterResource(R.drawable.ic_delete_comics),
                                contentDescription = stringResource(R.string.deleteSelectionsLabel)
                            )
                        }
                    }

                }
            )
        }
    )
}

@Composable
@Preview
fun ComicBookViewPreview() {
    VariantTheme {
        ComicBookView(
            COMIC_BOOK_LIST,
            false,
            emptyList(),
            onSetSelectionMode = { _ -> },
            onComicBookClicked = { _ -> },
            onDeleteComics = { })
    }
}

@Composable
@Preview
fun ComicBookViewWithSelectionsPreview() {
    VariantTheme {
        ComicBookView(
            COMIC_BOOK_LIST,
            true,
            listOf(COMIC_BOOK_LIST.get(0).path),
            onSetSelectionMode = { _ -> },
            onComicBookClicked = { _ -> },
            onDeleteComics = { })
    }
}