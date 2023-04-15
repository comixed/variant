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

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.comixedproject.prestige.android.R
import org.comixedproject.prestige.android.ui.app.LOGTAG
import org.comixedproject.prestige.android.ui.library.LibraryListEntryView
import org.comixedproject.prestige.model.library.Library

const val TAG_LIBRARY_LIST = "library.list."
const val TAG_ADD_LIBRARY = "library.button.add"
const val TAG_LIBRARY_ENTRY = "library.button.remove."

/**
 * <code>LibraryListView</code> displays the list of libraries.
 *
 * @author Darryl L. Pierce
 */
@Composable
fun LibraryListView(
    libraries: List<Library>,
    onAddLibrary: () -> Unit,
    onEditLibrary: (Library) -> Unit,
    onRemoveLibrary: (Library) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    Log.d(LOGTAG, "Add library button clicked")
                    onAddLibrary()
                },
                modifier = Modifier.testTag(TAG_ADD_LIBRARY)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(R.string.description_add_library)
                )
            }
        },
        modifier = modifier.fillMaxWidth()
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.library_list_title),
                style = MaterialTheme.typography.h2
            )


            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .testTag(TAG_LIBRARY_LIST)
            ) {
                items(items = libraries,
                    key = { it.libraryId },
                    itemContent = { library ->
                        LibraryListEntryView(
                            library = library,
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colors.primaryVariant,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .testTag(TAG_LIBRARY_ENTRY + library.libraryId),
                            onEditLibrary = onEditLibrary,
                            onRemoveLibrary = onRemoveLibrary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                )
            }
        }
    }
}


@Preview
@Composable
fun LibraryListPreview() {
    LibraryListView(
        libraries = listOf(
            Library(),
            Library(),
            Library(),
            Library(),
            Library(),
            Library(),
            Library(),
            Library(),
            Library()
        ),
        onAddLibrary = {},
        onEditLibrary = { _ -> {} },
        onRemoveLibrary = {})
}