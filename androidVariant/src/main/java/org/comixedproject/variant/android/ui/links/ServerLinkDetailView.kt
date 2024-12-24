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

import android.util.Base64
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.platform.Logger

private const val TAG = "ServerLinkDetailView"

@Composable
fun ServerLinkDetailView(
    server: Server,
    title: String,
    coverUrl: String,
    showOpen: Boolean,
    onDownloadEntry: () -> Unit,
    onStreamEntry: () -> Unit,
    onOpenEntry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        );

        Button(onClick = onDownloadEntry) {
            Text("Download")
        }

        Button(onClick = onStreamEntry) {
            Text("Stream")
        }

        if (showOpen) {
            Button(onClick = onOpenEntry) {
                Text("Open")
            }
        }

        Spacer(modifier = Modifier)

        Logger.d(TAG, "Async loading cover image: ${coverUrl}")
        val authorization = Base64.encodeToString(
            "${server.username}:${server.password}".toByteArray(),
            Base64.NO_WRAP,
        )

        Logger.d(TAG, "Authorization: ${authorization}")
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(coverUrl)
                .addHeader("Authorization", "Basic ${authorization}")
                .crossfade(true)
                .build(),
            contentDescription = coverUrl,
            contentScale = ContentScale.Inside,
            modifier = Modifier.fillMaxWidth()
        )
    }

}

@Composable
@Preview
fun ServerLinkDetail_withDownload_Preview() {
    VariantTheme {
        ServerLinkDetailView(
            Server(
                1L,
                "Server 1",
                "http://server1.org:7171/opds",
                "admin@comixedproject.org",
                "p455w0rD"
            ),
            "The Title",
            "The cover URL",
            true,
            onDownloadEntry = {},
            onStreamEntry = {},
            onOpenEntry = {})
    }
}

@Composable
@Preview
fun ServerLinkDetail_withOpen_Preview() {
    VariantTheme {
        ServerLinkDetailView(
            Server(
                1L,
                "Server 1",
                "http://server1.org:7171/opds",
                "admin@comixedproject.org",
                "p455w0rD"
            ),
            "The Title",
            "The cover URL",
            false,
            onDownloadEntry = {},
            onStreamEntry = {},
            onOpenEntry = {})
    }
}