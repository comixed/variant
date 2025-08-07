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

package org.comixedproject.variant.android.view.reading

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.COMIC_BOOK_LIST
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.model.library.ComicBook
import org.comixedproject.variant.platform.Log

private const val TAG = "ReadingView"

@Composable
fun ReadingView(
    comicBook: ComicBook,
    onStopReading: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentPage by remember { mutableIntStateOf(0) }
    var showPageOverlay by remember { mutableStateOf(false) }

    PageNavigationView(
        comicBook.path,
        comicBook.filename,
        comicBook.pages.get(currentPage).filename,
        comicBook.pages.get(currentPage).filename,
        currentPage,
        comicBook.pages.size,
        showPageOverlay,
        onChangePage = {
            Log.debug(TAG, "Going to page ${it}")
            currentPage = it
        },
        onStopReading = onStopReading,
        onToggleShowOverlay = {
            Log.debug(TAG, "Toggling showing overlay")
            showPageOverlay = !showPageOverlay
        },
        modifier = modifier
    )
}

@Composable
@Preview
fun ReadingViewPreview() {
    VariantTheme { ReadingView(COMIC_BOOK_LIST.get(0), onStopReading = {}) }
}