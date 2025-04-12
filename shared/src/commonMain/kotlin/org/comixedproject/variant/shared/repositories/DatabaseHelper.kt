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

import app.cash.sqldelight.db.SqlDriver
import kotlinx.datetime.Clock
import org.comixedproject.variant.database.VariantDb
import org.comixedproject.variant.db.ServerLinksDb
import org.comixedproject.variant.db.ServersDb
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.model.server.ServerLink

class DatabaseHelper(
    sqlDriver: SqlDriver,
) {
    private val database: VariantDb = VariantDb(sqlDriver)

    fun loadServers(): List<ServersDb> = database.tableQueries.loadAllServers().executeAsList()

    fun createServer(
        name: String,
        url: String,
        username: String,
        password: String,
    ) = database.tableQueries.createServer(
        name,
        url,
        username,
        password
    )

    fun loadServer(serverId: Long) = database.tableQueries.loadServer(serverId).executeAsOne()

    fun updateServer(
        serverId: Long,
        name: String,
        url: String,
        username: String,
        password: String,
        accessedDate: Long? = null,
    ) {
        database.tableQueries.updateServer(
            name,
            url,
            username,
            password,
            accessedDate,
            serverId
        )
    }

    fun deleteServer(serverId: Long) {
        database.tableQueries.deleteServer(serverId)
    }

    fun markServerAsAccessed(serverId: Long) {
        database.tableQueries.markServerAsAccessed(
            Clock.System.now().toEpochMilliseconds(),
            serverId
        )
    }

    fun loadAllLinks(): List<ServerLinksDb> = database.tableQueries.loadAllLinks().executeAsList()

    fun saveLinksForServer(
        server: Server,
        directory: String,
        serverLinks: List<ServerLink>,
    ) {
        val serverId = server.serverId!!
        val existingLinks =
            database.tableQueries.loadLinksForDirectory(serverId, directory).executeAsList()
        existingLinks.forEach { link -> database.tableQueries.deleteExistingLink(link.server_link_id) }
        serverLinks.forEach { link ->
            database.tableQueries.createLink(
                serverId,
                link.directory,
                link.identifier,
                link.title,
                link.coverUrl,
                link.downloadLink,
                link.linkType.name
            )
        }
    }

    fun markParentLinkAsAccessed(serverId: Long, directory: String) {
        database.tableQueries.markParentLinkAsAccessed(
            Clock.System.now().toEpochMilliseconds(),
            serverId,
            directory
        )
    }

    fun loadLinksForDirectory(serverId: Long, directory: String) =
        database.tableQueries.loadLinksForDirectory(serverId, directory).executeAsList()

    fun loadLink(serverId: Long, directory: String) =
        database.tableQueries.loadLink(serverId, directory).executeAsOneOrNull()
}
