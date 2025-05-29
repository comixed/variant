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

package org.comixedproject.variant.database

import app.cash.sqldelight.db.SqlDriver
import org.comixedproject.variant.model.library.DirectoryEntry

class DatabaseHelper(sqlDriver: SqlDriver) {
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

    fun updateServer(
        serverId: Long,
        name: String,
        url: String,
        username: String,
        password: String
    ) {
        database.tableQueries.updateServer(
            name,
            url,
            username,
            password,
            serverId
        )
    }

    fun deleteServer(serverId: Long) {
        database.tableQueries.deleteServer(serverId)
    }

    fun loadDirectoryContents(serverId: Long, directory: String) =
        database.tableQueries.loadDirectoryContents(serverId, directory).executeAsList()

    fun saveDirectoryContent(directoryEntry: DirectoryEntry) =
        database.tableQueries.saveDirectoryContent(
            directoryEntry.directoryId,
            directoryEntry.serverId,
            directoryEntry.title,
            directoryEntry.path,
            directoryEntry.parent,
            directoryEntry.filename,
            when (directoryEntry.isDirectory) {
                false -> 0
                else -> 1
            },
            directoryEntry.coverUrl
        )

    fun deleteDirectoryContents(serverId: Long, directory: String) =
        database.tableQueries.deleteDirectoryContents(serverId, directory)

    fun findDirectory(path: String) = database.tableQueries.findDirectory(path).executeAsOneOrNull()
}