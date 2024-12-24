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

package org.comixedproject.variant.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.call.body
import io.ktor.client.plugins.onDownload
import io.ktor.client.request.get
import kotlinx.coroutines.launch
import org.comixedproject.variant.android.koin
import org.comixedproject.variant.android.net.createDownloadHttpClient
import org.comixedproject.variant.android.net.createOpdsHttpClient
import org.comixedproject.variant.shared.VariantAppContext
import org.comixedproject.variant.shared.data.ServerLinkRepository
import org.comixedproject.variant.shared.manager.FileContentManager
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.model.server.ServerLink
import org.comixedproject.variant.shared.platform.Logger
import org.readium.r2.shared.util.AbsoluteUrl
import org.readium.r2.shared.util.asset.AssetRetriever
import org.readium.r2.shared.util.format.FormatHints

private const val TAG = "PublicationViewModel"

class PublicationViewModel : ViewModel() {
    private val fileContentManager: FileContentManager = koin.get()
    private val serverLinkRepository: ServerLinkRepository = koin.get()

    fun downloadPublication(server: Server, serverLink: ServerLink, reload: Boolean) {
        if (reload || !fileContentManager.contentFound(server, serverLink)) {
            Logger.d(
                TAG,
                "Downloading publication: reload=${reload} server=${server.name} serverLink=${serverLink.serverLinkId}"
            )

            viewModelScope.launch {
                doDownloadPublication(server, serverLink)
            }
        }
    }

    fun streamPublication(server: Server, serverLink: ServerLink) {
        Logger.d(
            TAG,
            "Streaming publication: server=${server.name} serverLink=${serverLink.serverLinkId}"
        )

        viewModelScope.launch {
            doStreamPublication(server, serverLink, true)
        }
    }

    suspend fun doDownloadPublication(server: Server, serverLink: ServerLink) {
        val response = createDownloadHttpClient(server).get(serverLink.downloadLink) {
            onDownload { bytesSentTotal, contentLength ->
                Logger.d(
                    TAG,
                    "Downloaded ${bytesSentTotal}/${contentLength} bytes"
                )
            }
        }

        Logger.d(TAG, "Storing content")
        val filename = fileContentManager.storeContent(server, serverLink, response.body())
        this.serverLinkRepository.setStoredFilename(filename, serverLink)
    }

    suspend fun doStreamPublication(server: Server, serverLink: ServerLink, reload: Boolean) {
        if (!reload) {
            Logger.d(TAG, "Skipping download: content found locally")
        } else {
            Logger.d(TAG, "Fetching content")
            val httpClient = createOpdsHttpClient(server)
            val assetRetriever = AssetRetriever(
                contentResolver = VariantAppContext.get().contentResolver,
                httpClient = httpClient
            )
            val absoluteUrl = AbsoluteUrl(serverLink.downloadLink)
            Logger.d(TAG, "Retrieving asset: ${absoluteUrl}")

            val asset = assetRetriever.retrieve(
                absoluteUrl!!,
                FormatHints(
                    listOf(
                        "application/vnd.comicbook+zip",
                        "application/vnd.comicbook+rar",
                        "application/vnd.comicbook+octet-stream",
                    ), listOf("CBZ", "CBR", "CB7", "cbz", "cbr", "cb7")
                )
            )
                .onSuccess { publication ->
                    Logger.d(TAG, "Got my thing! ${publication}")
                }
                .onFailure { error ->
                    Logger.e(TAG, "Failed to open comic book: ${error}")
                }
        }
    }
}