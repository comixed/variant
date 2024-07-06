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

package org.comixedproject.variant.android.ui.links

import androidx.compose.foundation.clickable
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.model.server.ServerLink
import org.comixedproject.variant.shared.model.server.ServerLinkType

/**
 * <code>ServerLinkListItem</code> composes a single link for a server.
 *
 * @author Darryl L. Pierce
 */
@Composable
fun ServerLinkListItem(
    server: Server, link: ServerLink, onLoadDirectory: (Server, ServerLink) -> Unit
) {
    ListItem(
        headlineContent = { Text(link.title!!) },
        modifier = Modifier.clickable { onLoadDirectory(server, link) })
}

@Preview
@Composable
fun ServerLinkListItemPreview() {
    VariantTheme {
        ServerLinkListItem(Server(
            1L,
            "My Server",
            "http://www.comixedproject.org:7171/opds",
            "reader@comixedproject.org",
            "my!password"
        ), ServerLink(
            1L, 1L, "", "", "First Link", "", ServerLinkType.NAVIGATION
        ), onLoadDirectory = { _, _ -> })
    }
}