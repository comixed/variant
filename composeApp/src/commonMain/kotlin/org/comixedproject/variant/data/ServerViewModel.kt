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

package org.comixedproject.variant.data

import org.comixedproject.variant.model.Server

class ServerViewModel(private val serverRepository: ServerRepository) : BaseViewModel() {
    private val serverList: List<Server>
        get() = serverRepository.serverList

    var onServerListUpdated: ((List<Server>) -> Unit)? = null
        set(value) {
            field = value
            onServerListUpdated?.invoke(serverList)
        }

    fun createServer(name: String, url: String, username: String, password: String) {
        serverRepository.createServer(name, url, username, password)
        onServerListUpdated?.invoke(serverList)
    }

    fun removeServer(server: Server) {
        serverRepository.removeServer(server)
        onServerListUpdated?.invoke(serverList)
    }
}