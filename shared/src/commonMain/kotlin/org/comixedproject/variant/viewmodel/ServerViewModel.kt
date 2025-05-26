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

package org.comixedproject.variant.viewmodel

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.MutableStateFlow
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.launch
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.comixedproject.variant.database.repository.ServerRepository
import org.comixedproject.variant.model.Server
import org.comixedproject.variant.platform.Log

private const val TAG = "ServerViewModel"

open class ServerViewModel(val serverRepository: ServerRepository) : ViewModel() {
    private val _serverList =
        MutableStateFlow<List<Server>>(viewModelScope, serverRepository.servers)

    @NativeCoroutinesState
    val serverList: StateFlow<List<Server>> = _serverList.asStateFlow()

    private val _editingServer = MutableStateFlow<Server?>(viewModelScope, null)

    @NativeCoroutinesState
    val editingServer: StateFlow<Server?> = _editingServer.asStateFlow()

    fun editServer(server: Server?) {
        server?.let {
            Log.debug(TAG, "Editing server: ${it.name}")
        }
        viewModelScope.launch {
            _editingServer.emit(server)
        }
    }

    fun saveServer(server: Server) {
        viewModelScope.launch {
            Log.debug(TAG, "Saving server: ${server.name} ${server.url}")
            serverRepository.save(server)
            doReloadServers()
        }
    }

    @NativeCoroutines
    suspend fun doReloadServers() {
        Log.debug(TAG, "Loading server list")
        val servers = serverRepository.servers
        _serverList.emit(servers)
    }
}