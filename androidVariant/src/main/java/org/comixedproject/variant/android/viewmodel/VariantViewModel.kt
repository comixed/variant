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

package org.comixedproject.variant.android.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.comixedproject.variant.android.koin
import org.comixedproject.variant.android.model.SERVER_LIST
import org.comixedproject.variant.shared.data.ServerRepository
import org.comixedproject.variant.shared.model.server.Server


class VariantViewModel : ViewModel() {
    private val serverRespository: ServerRepository = koin.get()
    private val _serversFlow: MutableStateFlow<List<Server>> by lazy {
        MutableStateFlow(
            SERVER_LIST, // serverRespository.servers,
        )
    }
    val serverList = _serversFlow.asStateFlow()

    fun saveServer(server: Server) {
        serverRespository.saveServer(server)
    }

    private val _currentServer = MutableStateFlow<Server?>(null)
    val currentServer: StateFlow<Server?> = _currentServer

    fun setCurrentServer(server: Server?) {
        this._currentServer.value = server
    }
}