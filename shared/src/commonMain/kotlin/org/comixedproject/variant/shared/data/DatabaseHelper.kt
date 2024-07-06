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

package org.comixedproject.variant.shared.data

import app.cash.sqldelight.db.SqlDriver
import org.comixedproject.variant.VariantDb
import org.comixedproject.variant.db.ServerLinksDb
import org.comixedproject.variant.db.ServersDb
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.model.server.ServerLink

class DatabaseHelper(sqlDriver: SqlDriver) {
    private val database: VariantDb = VariantDb(sqlDriver)

    fun loadServers(): List<ServersDb> = database.tableQueries.loadAllServers().executeAsList()

    fun createServer(name: String, url: String, username: String, password: String) {
        return database.tableQueries.createServer(
            name,
            url,
            username,
            password
        )
    }

    fun updateServer(
        serverId: Long,
        name: String,
        url: String,
        username: String,
        password: String
    ) {
        database.tableQueries.updateServer(name, url, username, password, serverId)
    }

    fun deleteServer(serverId: Long) {
        database.tableQueries.deleteServer(serverId)
    }

    fun loadAllLinks(): List<ServerLinksDb> =
        database.tableQueries.loadAllLinks().executeAsList()

    fun loadLinks(serverId: Long, directory: String): List<ServerLinksDb> =
        database.tableQueries.loadLinksForParent(serverId, directory)
            .executeAsList()

    fun saveLinksForServer(
        server: Server,
        directory: String,
        serverLinks: List<ServerLink>
    ) {
        val incomingPaths = serverLinks.map { it.href }
        val serverId = server.serverId!!
        val existingLinks =
            database.tableQueries.loadLinksForParent(serverId, directory).executeAsList()
        existingLinks.filter { link -> !incomingPaths.contains(link.href) }
            .forEach { link -> database.tableQueries.deleteExistingLink(link.serverLinkId) }
        val existingPaths = existingLinks.map { it.href }
        serverLinks.filter { !existingPaths.contains(it.href) }.forEach { link ->
            database.tableQueries.createLink(
                serverId,
                link.directory,
                link.identifier,
                link.title,
                link.href,
                link.linkType.name
            )
        }
    }
}