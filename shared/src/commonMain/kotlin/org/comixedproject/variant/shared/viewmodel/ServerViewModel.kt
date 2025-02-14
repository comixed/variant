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

package org.comixedproject.variant.shared.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.platform.Logger
import org.comixedproject.variant.shared.repositories.ServerRepository

private val TAG = "VariantViewModel"

/**
 * <code>VariantViewModel</code> provides a single source for application state on Android devices.
 *
 * @author Darryl L. Pierce
 */
class ServerViewModel(
    val serverRepository: ServerRepository
) : BaseViewModel() {
    private val _serverListFlow: MutableStateFlow<List<Server>> by lazy {
        MutableStateFlow(
            serverRepository.servers
        )
    }
    val serverList = _serverListFlow.asStateFlow()

    var onServerListUpdated: ((List<Server>) -> Unit)? = null

    /**
     * Saves the given server to storage.
     *
     * @param server the server
     */
    fun saveServer(server: Server) {
        Logger.d(TAG, "Saving server: name=${server.name}")
        serverRepository.save(server)
        _serverListFlow.tryEmit(serverRepository.servers)
        onServerListUpdated?.invoke(serverRepository.servers)
    }

    /**
     * Deletes the given server from store.
     *
     * @param server the server
     */
    fun deleteServer(server: Server) {
        server.serverId.let { serverId ->
            if (serverId != null) {
                Logger.d(TAG, "Deleting server: name=${server.name}")
                serverRepository.deleteServer(serverId)
                _serverListFlow.tryEmit(serverRepository.servers)
                onServerListUpdated?.invoke(serverRepository.servers)
            }
        }
    }
}