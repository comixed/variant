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
import org.comixedproject.variant.database.ServersDb
import org.comixedproject.variant.model.Server
import org.comixedproject.variant.platform.Log

private const val TAG = "ServerRepository"

class ServerRepository(val databaseHelper: DatabaseHelper) {
    val servers: List<Server>
        get() = databaseHelper.loadServers().map(ServersDb::map)

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

    fun deleteServer(id: Long) {
        Log.debug(TAG, "Deleting server: id=${id}")
        databaseHelper.deleteServer(id)
    }
}


fun ServersDb.map() =
    Server(
        serverId = this.id,
        name = this.name,
        url = this.url,
        username = this.username,
        password = this.password
    )