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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.model.SERVER_LINK_LIST
import org.comixedproject.variant.android.model.SERVER_LIST
import org.comixedproject.variant.shared.VARIANT_USER_AGENT
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.model.server.ServerLink
import org.comixedproject.variant.shared.model.server.ServerLinkType
import org.comixedproject.variant.shared.net.encodeCredentials
import org.comixedproject.variant.shared.platform.Log

private val TAG = "ServerLinkDetailView"

const val TAG_CLOSE_BUTTON = "button.close"
const val TAG_TITLE_TEXT = "text.title"
const val TAG_SUBTITLE_TEXT = "text.subtitle"

@Composable
fun ServerLinkDetailView(server: Server, serverLink: ServerLink, onClose: () -> Unit) {
    Scaffold(
        floatingActionButton = {
            IconButton(
                onClick = {
                    Log.info(TAG, "Closing details view")
                    onClose()
                },
                modifier = Modifier.testTag(TAG_CLOSE_BUTTON)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.closeButton)
                )
            }
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                Text(
                    "${serverLink.title}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(TAG_TITLE_TEXT),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    maxLines = 1, overflow = TextOverflow.Ellipsis
                )
                Text(
                    "${serverLink.downloadLink}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(TAG_SUBTITLE_TEXT),
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    maxLines = 1, overflow = TextOverflow.Ellipsis
                )

                val model = when (server.username.isNotEmpty()) {
                    true -> {
                        val authorization = encodeCredentials(server.username, server.password)
                        ImageRequest.Builder(LocalContext.current)
                            .data(serverLink.coverUrl)
                            .addHeader("Authorization", "Basic ${authorization}")
                            .addHeader("User-Agent", VARIANT_USER_AGENT)
                            .build()
                    }

                    false -> serverLink.coverUrl
                }

                AsyncImage(
                    model = model,
                    contentDescription = serverLink.coverUrl,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth()
                )

            }
        }
    )
}

@Composable
@Preview
fun ServerLinkDetialPreview() {
    VariantTheme {
        ServerLinkDetailView(
            SERVER_LIST.get(0),
            SERVER_LINK_LIST.filter { entry -> entry.linkType == ServerLinkType.PUBLICATION }
                .first(), onClose = {})
    }
}