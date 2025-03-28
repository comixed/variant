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

import kotlinx.datetime.Instant
import org.comixedproject.variant.db.ServersDb
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.platform.Log

private val TAG = "ServerRepository"

/**
 * <code>ServerRepository</code> provides methods for working with saved instances of {@link Server}.
 *
 * @author Darryl L. Pierce
 */
class ServerRepository(
    val databaseHelper: DatabaseHelper,
) {
    val servers: List<Server>
        get() = databaseHelper.loadServers().map(ServersDb::map)

    /**
     * Creates or updates the provided server.
     *
     * @param server the server
     */
    fun save(server: Server) {
        server.serverId.let { id ->
            var password = when (server.password.isEmpty()) {
                true -> "[NONE]"
                else -> server.password.get(0) + "****"
            }
            if (id == null) {
                Log.debug(
                    TAG,
                    "Creating server: name=${server.name} url=${server.url} username=${server.username} password=${password}"
                )
                databaseHelper.createServer(
                    server.name,
                    server.url,
                    server.username,
                    server.password
                )
            } else {
                Log.debug(
                    TAG,
                    "Updating server: id=${id} name=${server.name} url=${server.url} username=${server.username} password=${password}"
                )
                databaseHelper.updateServer(
                    id,
                    server.name,
                    server.url,
                    server.username,
                    server.password,
                )
            }
        }
    }

    /**
     * Deletes the server with the given id.
     *
     * @param id the server id
     */
    fun deleteServer(id: Long) {
        Log.debug(TAG, "Deleting server: id=${id}")
        databaseHelper.deleteServer(id)
    }

    fun markServerAsAccessed(server: Server) {
        server.serverId?.let { serverId ->
            Log.debug(TAG, "Marking server as accessed: ${server.serverId}")
            databaseHelper.markServerAsAccessed(serverId)
        }
    }
}

fun ServersDb.map() =
    Server(
        serverId = this.server_id,
        name = this.name,
        url = this.url,
        username = this.username,
        password = this.password,
        accessedDate = when (this.accessed_date) {
            null -> null
            else -> Instant.fromEpochMilliseconds(this.accessed_date)
        }
    )
