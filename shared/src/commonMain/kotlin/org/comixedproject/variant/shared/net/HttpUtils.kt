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
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import org.comixedproject.variant.shared.VARIANT_USER_AGENT
import org.comixedproject.variant.shared.model.server.Server

expect fun getHttpClientEngineFactory(): HttpClientEngine

private val TAG = "HttpUtils"

/**
 * Creates a HTTP client to be used with the given server.
 *
 * @param server the server
 * @param url the url
 * @return the client
 */
fun createHttpClientFor(server: Server, url: String): HttpClient {
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
                    org.comixedproject.variant.shared.platform.Log.debug(TAG, message)
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
            url(url)
        }
    }
}