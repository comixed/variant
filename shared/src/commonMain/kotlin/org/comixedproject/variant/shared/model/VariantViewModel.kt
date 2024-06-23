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

import org.comixedproject.variant.shared.data.LinkRepository
import org.comixedproject.variant.shared.data.ServerRepository
import org.comixedproject.variant.shared.domain.LinkFeedData
import org.comixedproject.variant.shared.domain.ServiceLocator
import org.comixedproject.variant.shared.model.server.Link
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.platform.Logger

private const val TAG = "VariantViewModel"

class VariantViewModel(
    val serverRepository: ServerRepository,
    val linkRepository: LinkRepository
) : BaseViewModel(), LinkFeedData {

    private var _serverId: String? = null

    private var _directory: String? = null

    private var _loading = false

    val servers: List<Server>
        get() = serverRepository.servers

    val allLinks: List<Link> = linkRepository.loadAllLinks()

    val links: List<Link>
        get() = linkRepository.loadAllLinks()
            .filter { link -> _serverId != null && link.serverId == _serverId!! } // linksForParent(_server?.id, _directory?.orEmpty())
            .filter { link -> _directory != null && link.directory == _directory!! }.toList()

    var onServerUpdate: ((List<Server>) -> Unit)? = null
        set(value) {
            field = value
            onServerUpdate?.invoke(servers)
        }

    private val presenter by lazy { ServiceLocator.getFeedPresenter }

    fun saveServer(server: Server) {
        serverRepository.saveServer(server)
        onServerUpdate?.invoke(servers)
    }

    fun loadServerFeed(server: Server, directory: String) {
        if (_loading == false) {
            Logger.d(TAG, "Loading server feed: server=${server.name} directory=$directory")
            presenter.loadDirectoryOnServer(server, directory, this)
        } else {
            Logger.d(
                TAG,
                "Currently loading feed: ignoring request for server=${server.name} directory=$directory"
            )
        }
    }

    fun linksFor(server: Server, parentHref: String) =
        linkRepository.linksForParent(server.id!!, parentHref)

    fun deleteServer(server: Server) {
        serverRepository.deleteServer(server)
    }

    override fun onNewLinksReceived(
        server: Server,
        directory: String,
        links: List<Link>,
        exception: Exception?
    ) {
        if (exception != null) {
            Logger.e("Error receiving links", exception.toString())
        } else {
            Logger.d(TAG, "Saving ${links.size} link(s) from ${server.name} in ${directory}")
            linkRepository.saveLinksForServer(server.id!!, directory, links)
            _serverId = server.id
            _directory = directory
        }
    }
}