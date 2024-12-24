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

package org.comixedproject.variant.shared.net

import korlibs.crypto.encoding.Base64
import korlibs.io.lang.toByteArray

/**
 * Encodes the provided username and password for use in basic authorization headers.
 *
 * @param username the username
 * @param password the password
 * @return the encoded credentials
 */
fun encodeCredentials(username: String, password: String): String {
    return Base64.encode("${username}:${password}".toByteArray())
}