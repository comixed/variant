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

package org.comixedproject.variant.shared.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BasicAuthCredentials
import io.ktor.client.plugins.auth.providers.basic
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import korlibs.io.net.URL
import kotlinx.serialization.json.Json
import org.comixedproject.variant.shared.APP_NAME
import org.comixedproject.variant.shared.X_APP_NAME
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.platform.Logger
import kotlin.native.concurrent.ThreadLocal

private const val TAG = "FeedAPI"

@ThreadLocal
public object FeedAPI {
    private val nonStrictJson = Json { isLenient = true; ignoreUnknownKeys = true }

    public suspend fun loadDirectoryOnServer(server: Server, directory: String): HttpResponse {
        val url = when (directory) {
            "" -> server.url
            else -> URL.resolve(server.url, directory)
        }
        val client = HttpClient {
            install(Logging) {
                logger = HttpClientLogger
                level = LogLevel.HEADERS
            }

            install(Auth) {
                basic {
                    credentials {
                        BasicAuthCredentials(username = server.username, password = server.password)
                    }
                }
            }
        }

        Logger.d(TAG, "Loading directory on server: url=${url.toString()}")
        return client.get(url.toString()) {
            header(X_APP_NAME, APP_NAME)
            header("accept", "application/xml")
        }.body()
    }
}