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

package org.comixedproject.variant.android.ui.servers

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.comixedproject.variant.android.R
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.android.model.SERVER_LINK_LIST
import org.comixedproject.variant.android.model.SERVER_LIST
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.model.server.ServerLink
import org.comixedproject.variant.shared.model.server.ServerLinkType


@Composable
fun BrowseServerView(
    server: Server,
    serverLinkList: List<ServerLink>,
    onFollowLink: (Server, String, Boolean) -> Unit,
    onStopBrowsing: () -> Unit
) {

    var directory by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Row {
                IconButton(onClick = onStopBrowsing) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = stringResource(
                            R.string.stopBrowsingLabel
                        )
                    )
                }
                Text(
                    "${server.name}",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        bottomBar = {
            val directoryName = when (directory) {
                "" -> "[ROOT]"
                else -> directory
            }
            Text("${directoryName}")
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(serverLinkList) { serverLink ->
                if (serverLink.linkType == ServerLinkType.NAVIGATION) {
                    NavigationLinkView(serverLink, onLoadLink = { link ->
                        directory = link
                        onFollowLink(server, link, false)
                    })
                } else {
                    PublicationLinkView(serverLink, onLoadLink = { link ->
                        directory = link
                        onFollowLink(server, link, false)
                    })
                }
            }
        }
    }
}

@Composable
@Preview
fun BrowseServerPreview() {
    VariantTheme {
        BrowseServerView(
            SERVER_LIST.get(0),
            SERVER_LINK_LIST,
            onFollowLink = { _, _, _ -> },
            onStopBrowsing = { })
    }
}