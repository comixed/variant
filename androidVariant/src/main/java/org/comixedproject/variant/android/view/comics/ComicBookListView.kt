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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.comixedproject.variant.android.COMIC_BOOK_LIST
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.model.library.ComicBook
import org.comixedproject.variant.platform.Log

private val TAG = "ComicBookListView"

@Composable
fun ComicBookListView(
    comicBookList: List<ComicBook>,
    onClick: (ComicBook) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        content = { padding ->
            if (comicBookList.isEmpty()) {
                Log.debug(
                    TAG,
                    "No comics to display"
                )
                Text(stringResource(R.string.emptyComicListText))
            } else {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Adaptive(minSize = 128.dp),
                    verticalItemSpacing = 4.dp,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    content = {
                        items(comicBookList) { comicBook ->
                            ComicBookListItemView(
                                comicBook,
                                onClick = { onClick(it) },
                                modifier = Modifier.padding(padding)
                            )
                        }
                    })
            }
        }, modifier = modifier.padding(8.dp)
    )
}

@Composable
@Preview
fun ComicBookListViewPreview() {
    VariantTheme {
        ComicBookListView(COMIC_BOOK_LIST, onClick = {})
    }
}