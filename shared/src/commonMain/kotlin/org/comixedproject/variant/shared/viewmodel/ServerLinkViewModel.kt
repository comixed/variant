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
import org.comixedproject.variant.shared.model.server.ServerLink
import org.comixedproject.variant.shared.platform.Logger
import org.comixedproject.variant.shared.repositories.ServerLinkRepository

private val TAG = "ServerLinkViewModel"

/**
 * <code>ServerLinkViewModel</code> provides a shared view model for working with instances of {@link ServerLink}.
 *
 * @author Darrl L. Pierce
 */
class ServerLinkViewModel(val serverLinkRepository: ServerLinkRepository) : BaseViewModel() {
    private var currentServer: Server? = null
    private var directory: String = ""

    private val _serverLinkListFlow: MutableStateFlow<List<ServerLink>> by lazy {
        MutableStateFlow(
            serverLinkRepository.serverLinks
        )
    }

    val serverLinkList = _serverLinkListFlow.asStateFlow()
    var onServerLinkListUpdated: ((List<ServerLink>) -> Unit)? = null

    /**
     * Checks if there are any links for the given server and directory.
     *
     * @param server the server
     * @param directory the directory
     * @return true if there are links in the database, false otherwise
     */
    fun hasLinks(server: Server, directory: String): Boolean {
        return serverLinkRepository.serverLinks.filter { link -> link.directory == directory }
            .filter { link -> link.serverId == server.serverId }.isNotEmpty()
    }

    /**
     * Loads the links already in the database.
     *
     * @param server the server
     * @param directory the directory
     */
    fun loadLinks(server: Server, directory: String) {
        this.currentServer = server
        this.directory = directory
        _serverLinkListFlow.tryEmit(serverLinkRepository.serverLinks)
        onServerLinkListUpdated?.invoke(serverLinkRepository.serverLinks)
    }

    /**
     * Saves links to the database.
     *
     * @param server the server
     * @param directory the directory
     * @param links the links
     */
    fun saveLinks(server: Server, directory: String, links: List<ServerLink>) {
        Logger.d(TAG, "Saving ${links.size} server link(s)")
        this.serverLinkRepository.saveLinksForServer(server, directory, links)
        _serverLinkListFlow.tryEmit(this.serverLinkRepository.serverLinks)
        onServerLinkListUpdated?.invoke(this.serverLinkRepository.serverLinks)
    }

    fun getServerLinkId(server: Server, directory: String): Long {
        return this.serverLinkRepository.getServerLinkId(server, directory)
    }
}