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
import org.comixedproject.variant.shared.model.server.AcquisitionLink
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.platform.Logger

private const val TAG = "VariantViewModel"

class VariantViewModel(
    val serverRepository: ServerRepository,
    val linkRepository: LinkRepository
) : BaseViewModel(), LinkFeedData {

    private var _server: Server? = null
    val server: Server?
        get() = _server

    private var _directory: String = ""
    val directory: String
        get() = _directory

    private var _loading = false

    val servers: List<Server>
        get() = serverRepository.servers

    val allAcquisitionLinks: List<AcquisitionLink> = linkRepository.loadAllLinks()

    val acquisitionLinks: List<AcquisitionLink>
        get() = linkRepository.loadAllLinks()
            .filter { !_loading }
            .filter { link -> server != null && link.serverId == server?.id }
            .filter { link -> link.directory == _directory }
            .toList()

    var onServerListUpdated: ((List<Server>) -> Unit)? = null
        set(value) {
            field = value
            onServerListUpdated?.invoke(servers)
        }

    var onDisplayLinksUpdated: ((List<AcquisitionLink>) -> Unit)? = null
        set(value) {
            field = value
            onDisplayLinksUpdated?.invoke(acquisitionLinks)
        }

    var onAllLinksUpdated: ((List<AcquisitionLink>) -> Unit)? = null
        set(value) {
            field = value
            onDisplayLinksUpdated?.invoke(allAcquisitionLinks)
        }

    private val presenter by lazy { ServiceLocator.getFeedPresenter }

    fun saveServer(server: Server) {
        serverRepository.saveServer(server)
        onServerListUpdated?.invoke(serverRepository.servers)
    }

    fun loadServerFeed(server: Server, directory: String, reload: Boolean) {
        if (reload || _server != server || _directory != directory) {
            _server = server
            _directory = directory
            val needsReload = reload || acquisitionLinks.isEmpty()
            if (needsReload) {
                Logger.d(TAG, "Loading server feed: server=${server.name} directory=$directory")
                presenter.loadDirectoryOnServer(server, directory, this)
            } else {
                Logger.d(
                    TAG,
                    "Updating the link feed with ${acquisitionLinks.size} existing links"
                )
                onDisplayLinksUpdated?.invoke(acquisitionLinks)
                onAllLinksUpdated?.invoke(allAcquisitionLinks)
            }
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
        acquisitionLinks: List<AcquisitionLink>,
        exception: Exception?
    ) {
        if (exception != null) {
            Logger.e("Error receiving links", exception.toString())
        } else {
            Logger.d(
                TAG,
                "Saving ${acquisitionLinks.size} link(s) from ${server.name} in ${directory}"
            )
            linkRepository.saveLinksForServer(server.id!!, directory, acquisitionLinks)
            onDisplayLinksUpdated?.invoke(acquisitionLinks)
            onAllLinksUpdated?.invoke(allAcquisitionLinks)
        }
    }
}