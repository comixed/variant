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

package org.comixedproject.variant.shared.repositories

import org.comixedproject.variant.db.ServerLinksDb
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.model.server.ServerLink
import org.comixedproject.variant.shared.model.server.ServerLinkType

/**
 * <code>ServerLinkRepository</code> provides an API for storing and fetching instances of {@link AcquisitionLink}.
 *
 * @author Darryl L. Pierce
 */
class ServerLinkRepository(
    private val databaseHelper: DatabaseHelper,
    private val serverRepository: ServerRepository
) {
    val serverLinks: List<ServerLink>
        get() = databaseHelper.loadAllLinks().map(ServerLinksDb::map)

    fun saveLinksForServer(
        server: Server,
        directory: String,
        serverLinks: List<ServerLink>,
    ) {
        databaseHelper.saveLinksForServer(server, directory, serverLinks)
    }

    fun setStoredFilename(filename: String, serverLink: ServerLink) {
        databaseHelper.setStoredFilename(filename, serverLink)
    }
}

fun ServerLinksDb.map() =
    ServerLink(
        serverLinkId = this.serverLinkId,
        serverId = this.serverId,
        directory = this.directory,
        identifier = this.identifier,
        title = this.title,
        coverUrl = this.coverUrl,
        downloadLink = this.downloadLink,
        linkType = ServerLinkType.valueOf(this.linkType),
        downloaded = false
    )
