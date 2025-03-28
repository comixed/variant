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

package org.comixedproject.variant.shared.net

import io.ktor.client.plugins.onDownload
import io.ktor.client.request.prepareGet
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsChannel
import io.ktor.util.toByteArray
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.platform.Log

private val TAG = "OpdsUtils"

/**
 * Loads the contents of a directory from an OPDS server. It provides status updates during the download.
 *
 * @param server the server
 * @param directory the directory
 * @param onProgress the status callback
 * @param onSuccess the success callback
 * @param onFailure the failure callback
 */
suspend fun loadServerDirectory(
    server: Server,
    directory: String,
    onProgress: (Long, Long) -> Unit,
    onSuccess: (ByteArray) -> Unit,
    onFailure: () -> Unit
) {
    try {
        createHttpClientFor(server, directory).prepareGet {
            url(directory)
            onDownload { bytesSentTotal, contentLength ->
                onProgress(
                    bytesSentTotal,
                    contentLength
                )
            }

        }.execute { response ->
            if (response.status.value in 200..299) {
                val content = response.bodyAsChannel()
                onSuccess(content.toByteArray())
            } else {
                Log.error(TAG, "Failed to download directory")
                onFailure()
            }
        }
    } catch (error: Throwable) {
        Log.error(TAG, "Exception while downloading directory: ${error}")
        onFailure()
    }
}