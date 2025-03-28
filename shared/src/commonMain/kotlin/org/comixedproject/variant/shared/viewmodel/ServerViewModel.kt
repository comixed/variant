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

import com.rickclephas.kmp.observableviewmodel.MutableStateFlow
import com.rickclephas.kmp.observableviewmodel.ViewModel
import kotlinx.coroutines.flow.asStateFlow
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.platform.Log
import org.comixedproject.variant.shared.repositories.ServerRepository

private val TAG = "VariantViewModel"

/**
 * <code>VariantViewModel</code> provides a single source for application state on Android devices.
 *
 * @author Darryl L. Pierce
 */
class ServerViewModel(
    val serverRepository: ServerRepository
) : ViewModel() {
    private val _serverListFlow =
        MutableStateFlow<List<Server>>(viewModelScope, serverRepository.servers)
    val serverList = _serverListFlow.asStateFlow()

    private var _currentServer = MutableStateFlow<Server?>(viewModelScope, null)
    val currentServer = _currentServer.asStateFlow()

    private var _activity = MutableStateFlow(viewModelScope, ServerActivity.LIST_SERVERS)
    val activity = _activity.asStateFlow()

    var onServerListUpdated: ((List<Server>) -> Unit)? = null

    fun getById(id: Long): Server? {
        return this.serverRepository.getById(id)
    }

    fun addServer() {
        Log.debug(TAG, "Preparing to add a new server")
        _activity.tryEmit(ServerActivity.ADD_SERVER)
    }

    fun editServer(server: Server) {
        Log.debug(TAG, "Preparing to edit server: ${server.name}")
        _currentServer.tryEmit(server)
        _activity.tryEmit(ServerActivity.EDIT_SERVER)
    }

    /**
     * Saves the given server to storage.
     *
     * @param server the server
     */
    fun saveServer(server: Server) {
        Log.debug(TAG, "Saving server: name=${server.name}")
        serverRepository.save(server)
        _serverListFlow.tryEmit(serverRepository.servers)
        onServerListUpdated?.invoke(serverRepository.servers)
        _activity.tryEmit(ServerActivity.LIST_SERVERS)
    }

    fun cancelEditingServer() {
        Log.debug(TAG, "Canceling server editing")
        _activity.tryEmit(ServerActivity.LIST_SERVERS)
    }

    fun confirmDeleteServer(server: Server) {
        Log.debug(TAG, "Confirming server deletion: name=${server.name}")
        _currentServer.tryEmit(server)
    }

    /**
     * Deletes the given server from store.
     *
     * @param server the server
     */
    fun deleteServer(server: Server) {
        server.serverId.let { serverId ->
            if (serverId != null) {
                Log.debug(TAG, "Deleting server: name=${server.name}")
                serverRepository.deleteServer(serverId)
                _serverListFlow.tryEmit(serverRepository.servers)
                onServerListUpdated?.invoke(serverRepository.servers)
            }
        }
        _activity.tryEmit(ServerActivity.LIST_SERVERS)
    }

    fun browseServer(server: Server) {
        _currentServer.tryEmit(server)
        _activity.tryEmit(ServerActivity.BROWSE_SERVER)
    }

    fun stopBrowsingServer() {
        _activity.tryEmit(ServerActivity.LIST_SERVERS)
    }
}