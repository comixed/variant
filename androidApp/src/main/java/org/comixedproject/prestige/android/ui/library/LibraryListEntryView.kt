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

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Computer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.comixedproject.prestige.android.R
import org.comixedproject.prestige.model.library.Library

@Composable
fun LibraryListEntryView(library: Library) {
    Row(modifier = Modifier.padding(24.dp)) {
        Icon(
            Icons.Outlined.Computer,
            contentDescription = library.name,
            tint = Color.Blue,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = library.name,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle1
            )

            Spacer(modifier = Modifier.height(4.dp))

            Column {
                Text(
                    text = stringResource(
                        R.string.library_list_entry_hostname,
                        library.hostname,
                        library.port
                    ),
                    modifier = Modifier.padding(all = 4.dp),
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = stringResource(R.string.library_list_entry_username, library.username),
                    modifier = Modifier.padding(all = 4.dp),
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = stringResource(R.string.library_list_entry_password, library.password),
                    modifier = Modifier.padding(all = 4.dp),
                    style = MaterialTheme.typography.body1
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Button(onClick = { /*TODO*/ }) {
                Text("Open")
            }
            Button(onClick = { /*TODO*/ }) {
                Text("Edit")
            }
            Button(onClick = { /*TODO*/ }) {
                Text("Delete")
            }
        }
    }
}

@Preview
@Composable
fun PreviewLibraryView() {
    LibraryListEntryView(
        library = Library(
            0, "My Personal Library", "localhost", 7171, "reader@comixedproject.org", "my!password"
        )
    )
}