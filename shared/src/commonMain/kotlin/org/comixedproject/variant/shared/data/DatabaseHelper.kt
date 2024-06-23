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
import org.comixedproject.variant.db.LinksDb
import org.comixedproject.variant.db.ServersDb
import org.comixedproject.variant.shared.IDGenerator
import org.comixedproject.variant.shared.model.server.Link

class DatabaseHelper(sqlDriver: SqlDriver) {
    private val database: VariantDb = VariantDb(sqlDriver)

    fun loadServers(): List<ServersDb> = database.tableQueries.loadAllServers().executeAsList()

    fun createServer(name: String, url: String, username: String, password: String) {
        database.tableQueries.createServer(
            IDGenerator().toString(),
            name,
            url,
            username,
            password
        )
    }

    fun updateServer(id: String, name: String, url: String, username: String, password: String) {
        database.tableQueries.updateServer(name, url, username, password, id)
    }

    fun deleteServer(id: String) {
        database.tableQueries.deleteServer(id)
    }

    fun loadAllLinks(): List<LinksDb> = database.tableQueries.loadAllLinks().executeAsList()

    fun loadLinks(serverId: String, directory: String): List<LinksDb> =
        database.tableQueries.loadLinksForParent(serverId, directory)
            .executeAsList()

    fun saveLinksForServer(serverId: String, directory: String, links: List<Link>) {
        val incomingPaths = links.map { it.link }
        val existingLinks =
            database.tableQueries.loadLinksForParent(serverId, directory).executeAsList()
        existingLinks.filter { link -> !incomingPaths.contains(link.link) }
            .forEach { link -> database.tableQueries.deleteExistingLink(link.id) }
        val existingPaths = existingLinks.map { it.link }
        links.filter { !existingPaths.contains(it.link) }.forEach { link ->
            database.tableQueries.createLink(
                IDGenerator().toString(),
                serverId,
                link.linkId,
                link.directory,
                link.link,
                link.title,
                link.thumbnailURL.orEmpty()
            )
        }
    }
}