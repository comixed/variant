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

package org.comixedproject.variant.android.view.server

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.COMIC_BOOK_LIST
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.model.library.ComicBook
import org.comixedproject.variant.viewmodel.BrowsingState

private const val TAG = "ServerView"

@Composable
fun ServerView(
    browsingState: BrowsingState,
    comicBookList: List<ComicBook>,
    loading: Boolean,
    onLoadDirectory: (String, Boolean) -> Unit,
    onDownloadFile: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    BrowseServerView(
        browsingState.currentPath,
        browsingState.title,
        browsingState.parentPath,
        browsingState.contents,
        comicBookList,
        browsingState.downloadingState,
        loading,
        modifier = modifier,
        onLoadDirectory = { path, reload -> onLoadDirectory(path, reload) },
        onDownloadFile = { path, filename -> onDownloadFile(path, filename) },
    )
}

@Composable
@Preview
fun ServerViewPreview() {
    VariantTheme {
        ServerView(
            BrowsingState("", "", "", listOf(), listOf()),
            COMIC_BOOK_LIST,
            false,
            onLoadDirectory = { _, _ -> },
            onDownloadFile = { _, _ -> }
        )
    }
}