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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.shared.model.server.AcquisitionLink
import org.comixedproject.variant.shared.model.server.Server

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowseServerScreen(
    server: Server,
    acquisitionLinks: List<AcquisitionLink>,
    directory: String,
    onLoadDirectory: (Server, AcquisitionLink) -> Unit
) {
    Column {
        Text("[${acquisitionLinks.size}]${server.name}:${directory}")
        acquisitionLinks.forEach { link ->
            Text(link.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onLoadDirectory(server, link) })
        }
    }
}

@Preview
@Composable
fun ServerBrowsePreviewRoot() {
    VariantTheme {
        BrowseServerScreen(
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
fun BrowseServerScreenPreview() {
    VariantTheme {
        BrowseServerScreen(
            server = Server(
                "1",
                "My Server",
                "http://www.comixedproject.org:7171/opds",
                "reader@comixedproject.org",
                "my!password"
            ),
            listOf(
                AcquisitionLink(
                    null,
                    "1",
                    "",
                    "",
                    "",
                    "First Link",
                    null
                ), AcquisitionLink(
                    null,
                    "2",
                    "",
                    "",
                    "",
                    "Second Link",
                    null
                )
            ),
            "first/second/third",
            onLoadDirectory = { server, directory -> }
        )
    }
}