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

    fun loadDirectoryContents(directory: String) =
        database.tableQueries.loadDirectoryContents(directory).executeAsList()

    fun saveDirectoryContent(directoryEntry: DirectoryEntry) =
        database.tableQueries.saveDirectoryContent(
            directoryEntry.directoryId,
            directoryEntry.title,
            directoryEntry.path,
            directoryEntry.parent,
            directoryEntry.filename,
            directoryEntry.fileSize,
            when (directoryEntry.isDirectory) {
                false -> 0
                else -> 1
            },
            directoryEntry.coverUrl
        )

    fun deleteDirectoryContents(directory: String) =
        database.tableQueries.deleteDirectoryContents(directory)

    fun findDirectory(path: String) = database.tableQueries.findDirectory(path).executeAsOneOrNull()
}