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

package org.comixedproject.prestige.android.ui.library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.comixedproject.prestige.android.PrestigeTheme
import org.comixedproject.prestige.model.library.Library

@Composable
fun LibraryListEntryView(
    library: Library,
    onRemoveLibrary: (Library) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(Dp(160f))
            .padding(24.dp)
    ) {
        Column {
            Text(
                text = library.name,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle1
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                library.url,
                modifier = Modifier.padding(all = 4.dp),
                style = MaterialTheme.typography.subtitle2
            )
            Text(
                library.username,
                modifier = Modifier.padding(all = 4.dp),
                style = MaterialTheme.typography.body1
            )
        }

        Button(
            onClick = { onRemoveLibrary(library) }, modifier = Modifier
                .weight(0.2f)
                .align(CenterVertically)
        ) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = null)
        }
    }
}

@Preview
@Composable
fun PreviewLibraryView() {
    PrestigeTheme {
        LibraryListEntryView(
            library = Library(
                name = "My Home Server",
                url = "http://server.comixedproject.org:7171/opds",
                username = "reader@comixedproject.org", password = ""
            ),
            onRemoveLibrary = {}
        )
    }
}