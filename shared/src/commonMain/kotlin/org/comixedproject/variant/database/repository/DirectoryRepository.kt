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

package org.comixedproject.variant.database.repository

import org.comixedproject.variant.database.DatabaseHelper
import org.comixedproject.variant.model.Server
import org.comixedproject.variant.model.library.DirectoryEntry

private const val TAG = "ServerRepository"

class DirectoryRepository(val databaseHelper: DatabaseHelper) {
    fun loadDirectoryContents(server: Server, directory: String): List<DirectoryEntry> {
        val result = mutableListOf<DirectoryEntry>()

        server.serverId?.let { serverId ->
            databaseHelper.loadDirectoryContents(serverId, directory).forEach {
                result.add(
                    DirectoryEntry(
                        id = it.id,
                        directoryId = it.directory_id,
                        serverId = serverId,
                        title = it.title,
                        path = it.path,
                        parent = it.parent,
                        filename = it.filename,
                        isDirectory = when (it.is_directory) {
                            1L -> true
                            else -> false
                        },
                        coverUrl = it.cover_url
                    )
                )
            }
        }

        return result
    }

    fun saveDirectoryContents(server: Server, directory: String, contents: List<DirectoryEntry>) {
        server.serverId?.let { serverId ->
            databaseHelper.deleteDirectoryContents(serverId, directory)
            contents.forEach {
                databaseHelper.saveDirectoryContent(it)
            }
        }
    }

    fun findDirectory(path: String): DirectoryEntry? {
        databaseHelper.findDirectory(path)?.let { directory ->
            return DirectoryEntry(
                id = directory.id,
                directoryId = directory.directory_id,
                serverId = directory.server_id,
                title = directory.title,
                path = directory.path,
                parent = directory.parent,
                filename = directory.filename,
                isDirectory = when (directory.is_directory) {
                    1L -> true
                    else -> false
                },
                coverUrl = directory.cover_url
            )
        }

        return null
    }
}