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

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.shared.model.SERVER_LINK_LIST
import org.comixedproject.variant.shared.model.server.ServerLink
import org.comixedproject.variant.shared.model.server.ServerLinkType
import org.comixedproject.variant.shared.viewmodel.DownloadEntry

private val TAG = "ServerLinkListView"

@Composable
fun ServerLinkListView(
    serverLinkList: List<ServerLink>,
    currentDownloads: List<DownloadEntry>,
    onLoadLink: (ServerLink) -> Unit
) {
    LazyColumn {
        items(serverLinkList, key = { it.serverLinkId!! }) { serverLink ->
            val entry =
                currentDownloads.firstOrNull { it.downloadLink.equals(serverLink.downloadLink) }
            ServerLinkListItemView(
                serverLink,
                entry?.progress,
                onLoadLink = { onLoadLink(serverLink) }
            )
        }
    }
}

@Composable
@Preview
fun ServerLinkListPreview() {
    VariantTheme {
        val downloading = listOf(
            DownloadEntry(
                SERVER_LINK_LIST.filter { it.linkType == ServerLinkType.PUBLICATION }
                    .first().downloadLink,
                0.93
            )
        )

        ServerLinkListView(
            SERVER_LINK_LIST,
            downloading,
            onLoadLink = { _ -> })
    }
}