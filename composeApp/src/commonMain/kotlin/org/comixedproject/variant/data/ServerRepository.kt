/*
 * Variant - A digital comic book reading application for iPad, Android, and desktops.
 * Copyright (C) 2023, The ComiXed Project
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

package org.comixedproject.variant.data

import org.comixedproject.variant.db.ServerDb
import org.comixedproject.variant.model.Server

class ServerRepository(private val databaseHelper: DatabaseHelper) {
    val serverList: List<Server>
        get() = databaseHelper.loadAll().map(ServerDb::map)

    fun createServer(
        name: String,
        url: String,
        username: String,
        password: String,
        serverColor: String
    ) {
        databaseHelper.save(IdGenerator().toString(), name, url, username, password, serverColor)
    }

    fun updateServer(server: Server) {
        databaseHelper.update(
            server.id,
            server.name,
            server.url,
            server.username,
            server.password,
            server.serverColor
        )
    }

    fun removeServer(server: Server) {
        databaseHelper.delete(server.id)
    }
}

/**
 * Convert from a ServerDb to a Server.
 */
fun ServerDb.map() = Server(
    id = this.id,
    name = this.name,
    url = this.url,
    username = this.username,
    password = this.password,
    serverColor = this.serverColor
)