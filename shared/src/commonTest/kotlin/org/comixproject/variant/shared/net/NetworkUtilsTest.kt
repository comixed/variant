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

package org.comixproject.variant.shared.net

import org.comixedproject.variant.shared.net.encodeCredentials
import kotlin.test.Test
import kotlin.test.assertEquals

class NetworkUtilsTest {
    companion object {
        val username = "reader@comixedproject.org"
        val password = "mY!p455w0Rd!"
        val encodedPassword = "cmVhZGVyQGNvbWl4ZWRwcm9qZWN0Lm9yZzptWSFwNDU1dzBSZCE="
    }

    @Test
    fun testEncodeCredentials() {
        var result = encodeCredentials(username, password);

        assertEquals(encodedPassword, result)
    }
}