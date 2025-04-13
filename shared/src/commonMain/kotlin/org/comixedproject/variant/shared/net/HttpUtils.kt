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

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BasicAuthCredentials
import io.ktor.client.plugins.auth.providers.basic
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.onDownload
import io.ktor.client.request.header
import io.ktor.client.request.prepareGet
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.HttpHeaders
import io.ktor.util.toByteArray
import org.comixedproject.variant.shared.VARIANT_USER_AGENT
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.platform.Log

private val TAG = "HttpUtils"

expect fun getHttpClientEngineFactory(): HttpClientEngine

fun createHttpClient(server: Server, downloadLink: String): HttpClient {
    return HttpClient(getHttpClientEngineFactory()) {
        install(HttpTimeout) {
            socketTimeoutMillis = 60_000
            requestTimeoutMillis = 60_000
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    Log.debug(TAG, message)
                }
            }
        }

        if (server.username.isNotEmpty()) {
            install(Auth) {
                basic {
                    credentials {
                        BasicAuthCredentials(username = server.username, password = server.password)
                    }
                }
            }
        }

        defaultRequest {
            header(HttpHeaders.UserAgent, "$VARIANT_USER_AGENT")
            url(downloadLink)
        }
    }
}

suspend fun performGet(
    server: Server, downloadLink: String,
    onProgress: (Long, Long) -> Unit,
    onSuccess: (ByteArray) -> Unit,
    onFailure: () -> Unit
) {
    try {
        createHttpClient(server, downloadLink).prepareGet {
            url(downloadLink)
            onDownload { progress, total -> onProgress(progress, total) }
        }
            .execute { response ->
                if (response.status.value in 200..299) {
                    val content = response.bodyAsChannel()
                    onSuccess(content.toByteArray())
                } else {
                    Log.error(TAG, "Failed to download content")
                    onFailure()
                }
            }
    } catch (error: Throwable) {
        Log.error(TAG, "Failed to download file: ${error}")
        onFailure()
    }
}

suspend fun downloadFile(
    server: Server,
    url: String,
    onProgress: (Long, Long) -> Unit,
    onCompletion: (ByteArray) -> Unit
) {
    performGet(server, url, onProgress = onProgress, onSuccess = onCompletion, onFailure = {
        Log.error(TAG, "Failed to download ${url}")
    })
}