/*
 * Variant - A digital comic book reading application for iPad, Android, and desktops.
 * Copyright (C) 2023, The ComiXed Project
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

package org.comixedproject.variant.model;

import kotlinx.datetime.LocalDateTime

/**
 * <code>OPDSServerEntry</code> represents a single OPDS server.
 *
 * @author Darryl L. Pierce
 */
data class Server(
    val id: String,
    val name: String,
    val url: String,
    val username: String,
    val password: String
) {
    private var _lastAccessedOn: LocalDateTime? = null
    var lastAccessedOn: LocalDateTime?
        get() = _lastAccessedOn
        set(accessed) {
            _lastAccessedOn = accessed
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Server

        if (name != other.name) return false
        if (url != other.url) return false
        if (username != other.username) return false
        if (password != other.password) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + password.hashCode()
        return result
    }

}

val serverTemplate = Server("", "", "", "", "");