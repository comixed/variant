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

package org.comixedproject.variant.reader


import com.oldguy.common.io.ByteBuffer
import com.oldguy.common.io.RawFile
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BasicAuthCredentials
import io.ktor.client.plugins.auth.providers.basic
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.onDownload
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.prepareGet
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.Url
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.core.remaining
import io.ktor.utils.io.exhausted
import io.ktor.utils.io.readRemaining
import kotlinx.io.readByteArray
import kotlinx.serialization.json.Json
import org.comixedproject.variant.model.Server
import org.comixedproject.variant.model.net.LoadDirectoryResponse
import org.comixedproject.variant.network.HttpClientLogger
import org.comixedproject.variant.platform.Log
import kotlin.native.concurrent.ThreadLocal

private const val TAG = "ReaderAPI"

val READER_ROOT = "/reader/v1/root"

@ThreadLocal
public object ReaderAPI {

    const val VARIANT_USER_AGENT = "CX-Variant"

    private fun client(server: Server, url: Url): HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json { isLenient = true; ignoreUnknownKeys = true })
        }

        install(Logging) {
            logger = HttpClientLogger(TAG)
            level = LogLevel.ALL
        }

        install(Auth) {
            basic {
                sendWithoutRequest { true }
                credentials {
                    BasicAuthCredentials(username = server.username, password = server.password)
                }
            }
        }

        defaultRequest {
            header(HttpHeaders.UserAgent, "$VARIANT_USER_AGENT")
            url(url.toString())
        }
    }

    suspend fun loadDirectory(server: Server, url: Url): LoadDirectoryResponse =
        client(server, url).get {
            accept(ContentType.Application.Json)
        }.body()

    suspend fun downloadComic(
        server: Server,
        url: Url,
        output: RawFile,
        onProgress: (Long, Long) -> Unit
    ) {
        client(server, url).prepareGet {
            onDownload { received, total ->
                onProgress(received, total ?: 0)
            }
            accept(ContentType.Application.Zip)
            accept(ContentType.Application.OctetStream)
        }.execute { httpResponse ->
            val channel: ByteReadChannel = httpResponse.body()
            while (!channel.exhausted()) {
                val chunk = channel.readRemaining()
                Log.debug(TAG, "Writing ${chunk.remaining} bytes to output")
                output.write(ByteBuffer(bytes = chunk.readByteArray()))
            }
        }
    }
}