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

package org.comixedproject.variant.android.net

import org.comixedproject.variant.shared.VARIANT_USER_AGENT
import org.comixedproject.variant.shared.model.server.Server
import org.readium.r2.shared.util.http.DefaultHttpClient
import org.readium.r2.shared.util.http.HttpClient as OpdsHttpClient

/**
 * Returns a client object to connect to the specified server.
 *
 * @param server the server
 * @return an http client object
 */
fun createOpdsHttpClient(server: Server): OpdsHttpClient {
    return DefaultHttpClient(
        userAgent = VARIANT_USER_AGENT,
        callback = HttpCallback(server)
    )
}