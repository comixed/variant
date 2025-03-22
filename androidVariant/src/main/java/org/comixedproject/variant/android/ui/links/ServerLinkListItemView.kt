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

package org.comixedproject.variant.android.ui.links

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.model.SERVER_LINK_LIST
import org.comixedproject.variant.shared.model.server.ServerLink
import org.comixedproject.variant.shared.model.server.ServerLinkType
import org.comixedproject.variant.shared.platform.Logger

const val TAG_SERVER_LINK_ITEM = "server.link.item"
const val TAG_SERVER_LINK_TITLE = "server.link.title"
const val TAG_NAVIGATION_ICON = "icon.link.navigation"
const val TAG_PUBLICATION_ICON = "icon.link.publication"

private val TAG = "ServerLinkListItemView"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServerLinkListItemView(serverLink: ServerLink, onLoadLink: () -> Unit, onShowInfo: () -> Unit) {
    ElevatedCard(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                Logger.d(TAG, "Loading link: ${serverLink.downloadLink}")
                onLoadLink()
            }

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    Logger.d(
                        TAG,
                        "Link clicked: type=${serverLink.linkType} downloadLink=${serverLink.downloadLink}"
                    )
                    onLoadLink()
                }
                .testTag(TAG_SERVER_LINK_ITEM),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "${serverLink.title}",
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
                    .testTag(TAG_SERVER_LINK_TITLE)
            )

            Spacer(modifier = Modifier)

            when (serverLink.linkType) {
                ServerLinkType.NAVIGATION ->
                    IconButton(
                        onClick = {
                            Logger.d(TAG, "Navigation icon clicked")
                            onLoadLink()
                        },
                        modifier = Modifier.testTag(TAG_NAVIGATION_ICON)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = serverLink.title
                        )
                    }

                ServerLinkType.PUBLICATION ->
                    IconButton(
                        onClick = {
                            Logger.d(TAG, "Information icon clicked")
                            onShowInfo()
                        },
                        modifier = Modifier.testTag(TAG_PUBLICATION_ICON)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = serverLink.title
                        )
                    }
            }
        }
    }
}

@Composable
@Preview
fun ServerLinkItemPreview_navigationLink() {
    VariantTheme {
        ServerLinkListItemView(
            SERVER_LINK_LIST.first { entry -> entry.linkType == ServerLinkType.NAVIGATION },
            onLoadLink = { },
            onShowInfo = { })
    }
}

@Composable
@Preview
fun ServerLinkItemPreview_publicationLink() {
    VariantTheme {
        ServerLinkListItemView(
            SERVER_LINK_LIST.first { entry -> entry.linkType == ServerLinkType.PUBLICATION },
            onLoadLink = { },
            onShowInfo = { })
    }
}