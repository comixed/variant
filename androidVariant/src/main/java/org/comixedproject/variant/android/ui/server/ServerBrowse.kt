/*
 * Variant - A digital comic book reading application for the iPad and Android tablets.
 * Copyright (C) 2024, The ComiXed Project
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

package org.comixedproject.variant.android.ui.server

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.shared.model.server.Link
import org.comixedproject.variant.shared.model.server.Server

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServerBrowse(
    server: Server,
    links: List<Link>,
    directory: String,
    onLoadDirectory: (Server, Link) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(text = server.name)
                },
                navigationIcon = {
                    IconButton(
                        onClick = { }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Text("${server.url}${directory}")
            Text("Show ${links.size} entries")
            links.forEach { link ->
                Row {
                    Button(onClick = { onLoadDirectory(server, link) }) {
                        Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = link.title)
                    }
                    Text("[${link.title}]")
                }
            }
        }
    }
}

@Preview
@Composable
fun ServerBrowsePreviewRoot() {
    VariantTheme {
        ServerBrowse(
            server = Server(
                "1",
                "My Server",
                "http://www.comixedproject.org:7171/opds",
                "reader@comixedproject.org",
                "my!password"
            ),
            mutableListOf(),
            "",
            onLoadDirectory = { server, directory -> }
        )
    }
}

@Preview
@Composable
fun ServerBrowsePreviewChild() {
    VariantTheme {
        ServerBrowse(
            server = Server(
                "1",
                "My Server",
                "http://www.comixedproject.org:7171/opds",
                "reader@comixedproject.org",
                "my!password"
            ),
            mutableListOf(),
            "first/second/third",
            onLoadDirectory = { server, directory -> }
        )
    }
}