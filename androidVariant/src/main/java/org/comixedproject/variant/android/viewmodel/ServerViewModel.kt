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

package org.comixedproject.variant.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.comixedproject.variant.android.koin
import org.comixedproject.variant.shared.data.ServerRepository
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.platform.Logger

private val TAG = "ServerViewModel"

/**
 * <code>ServerViewModel</code> provides a view model for the server list.
 *
 * @author Darryl L. Pierce
 */
class ServerViewModel : ViewModel() {
    private val serverRespository: ServerRepository = koin.get()
    private val _serversFlow: MutableStateFlow<List<Server>> by lazy {
        MutableStateFlow(
            serverRespository.servers,
        )
    }
    val serverList = _serversFlow.asStateFlow()

    fun onSaveServer(
        serverId: Long?,
        name: String,
        url: String,
        username: String,
        password: String,
    ) {
        viewModelScope.launch {
            doCreateServer(serverId, name, url, username, password)
        }
    }

    suspend fun doCreateServer(
        serverId: Long?,
        name: String,
        url: String,
        username: String,
        password: String,
    ) {
        Logger.d(
            TAG,
            "Saving server: serverId=$serverId name=$name url=$url username=$username password=$password",
        )

        val server = Server(serverId, name, url, username, password)

        serverRespository.saveServer(server)
        _serversFlow.emit(serverRespository.servers)
    }
}
