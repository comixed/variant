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

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.ui.SERVER_LIST
import org.comixedproject.variant.android.ui.SERVER_NAVIGATION_LINK
import org.comixedproject.variant.android.ui.SERVER_PUBLICATION_LINK
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.model.server.ServerLink
import org.comixedproject.variant.shared.model.server.ServerLinkType

/**
 * <code>ServerLinkListItem</code> composes a single link for a server.
 *
 * @author Darryl L. Pierce
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServerLinkListItem(
    server: Server,
    link: ServerLink,
    onLoadDirectory: (Server, ServerLink) -> Unit,
    onShowLinkDetails: (Server, ServerLink) -> Unit
) {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
        ) {
            if (link.linkType == ServerLinkType.NAVIGATION) {
                Image(imageVector = Icons.AutoMirrored.Filled.List, contentDescription = "")
            } else {
                Image(imageVector = Icons.Filled.PlayArrow, contentDescription = "")
            }
        }

        ListItem(
            headlineContent = { Text(link.title!!) },
            modifier = Modifier.clickable {
                if (link.linkType == ServerLinkType.NAVIGATION) {
                    onLoadDirectory(server, link)
                } else {
                    onShowLinkDetails(server, link)
                }
            },
        )
    }
}

@Preview
@Composable
fun ServerLinkListItemPreview_Navigation() {
    VariantTheme {
        ServerLinkListItem(
            SERVER_LIST.get(0),
            SERVER_NAVIGATION_LINK,
            onLoadDirectory = { _, _ -> },
            onShowLinkDetails = { _, _ -> }
        )
    }
}

@Preview
@Composable
fun ServerLinkListItemPreview_Publication() {
    VariantTheme {
        ServerLinkListItem(
            SERVER_LIST.get(0),
            SERVER_PUBLICATION_LINK,
            onLoadDirectory = { _, _ -> },
            onShowLinkDetails = { _, _ -> }
        )
    }
}
