/*
 * Prestige - A digital comic book reading application.
 * Copyright (C) 2023, The ComiXed Project
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

package org.comixedproject.prestige.android.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.prestige.android.R
import org.comixedproject.prestige.android.ui.library.LibraryListEntryView
import org.comixedproject.prestige.android.ui.library.SampleData
import org.comixedproject.prestige.model.library.Library

/**
 * <code>LibraryListView</code> displays the list of libraries.
 *
 * @author Darryl L. Pierce
 */
@Composable
fun LibraryListView(
    libraries: List<Library> = SampleData.libraries,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = stringResource(R.string.library_list_title),
            style = MaterialTheme.typography.h2
        )

        LazyColumn(modifier) {
            items(libraries) { entry -> LibraryListEntryView(library = entry) }
        }
    }
}


@Preview
@Composable
fun LibraryListViewPreview() {
    LibraryListView()
}