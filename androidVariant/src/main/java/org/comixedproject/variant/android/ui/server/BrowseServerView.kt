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

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.ui.links.ServerLinkList
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.model.server.ServerLink
import org.comixedproject.variant.shared.model.server.ServerLinkType

/**
 * <code>BrowseServerView</code> composes a view for browsing the contents of a server.
 *
 * @author Darryl L. Pierce
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowseServerView(
    server: Server,
    serverLinks: List<ServerLink>,
    directory: String,
    onLoadDirectory: (Server, ServerLink) -> Unit
) {
    ServerLinkList(
        server,
        serverLinks,
        onLoadDirectory
    )
    Spacer(modifier = Modifier)
}

const val SERVER_ID = 100L
const val DIRECTORY = "/opds/test"

@Preview
@Composable
fun BrowseServerPreview() {
    VariantTheme {
        BrowseServerView(
            Server(
                SERVER_ID,
                "My Server",
                "http://www.comixedproject.org:7171/opds",
                "reader@comixedproject.org",
                "my!password"
            ),
            mutableListOf(
                ServerLink(
                    100L,
                    SERVER_ID,
                    DIRECTORY,
                    "/opds/link1",
                    "First Entry",
                    "", ServerLinkType.NAVIGATION
                ),
                ServerLink(
                    101L,
                    SERVER_ID,
                    DIRECTORY,
                    "/opds/link1",
                    "Second Entry",
                    "", ServerLinkType.NAVIGATION
                ),
                ServerLink(
                    102L,
                    SERVER_ID,
                    DIRECTORY,
                    "/opds/link1",
                    "Third Entry",
                    "", ServerLinkType.NAVIGATION
                ),
                ServerLink(
                    103L,
                    SERVER_ID,
                    DIRECTORY,
                    "/opds/link1",
                    "Fourth Entry",
                    "", ServerLinkType.NAVIGATION
                ),
                ServerLink(
                    104L,
                    SERVER_ID,
                    DIRECTORY,
                    "/opds/link1",
                    "Fifth Entry",
                    "", ServerLinkType.NAVIGATION
                )
            ),

            DIRECTORY,
            onLoadDirectory = { _, _ -> }
        )
    }
}