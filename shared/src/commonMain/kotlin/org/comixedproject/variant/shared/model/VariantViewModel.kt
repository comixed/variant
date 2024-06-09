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

package org.comixedproject.variant.shared.model

import org.comixedproject.variant.shared.data.ServerRepository
import org.comixedproject.variant.shared.model.server.Server

class VariantViewModel(val serverRepository: ServerRepository) : BaseViewModel() {
    val servers: List<Server>
        get() = serverRepository.servers

    var onServerUpdate: ((List<Server>) -> Unit)? = null
        set(value) {
            field = value
            onServerUpdate?.invoke(servers)
        }

    fun saveServer(server: Server) {
        serverRepository.saveServer(server)
        onServerUpdate?.invoke(servers)
    }

    fun deleteServer(server: Server) {
        serverRepository.deleteServer(server)
    }
}