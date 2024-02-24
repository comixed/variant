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

package org.comixedproject.variant.data

import org.comixedproject.variant.model.Server

/**
 * <code>ServerRepository</code> provides methods for working with persistent instances
 * of Server.
 *
 * @author Darryl L. Pierce ,mn
 */
class ServerRepository {
    private var _serverList: MutableList<Server> = mutableListOf(
        Server(
            "1",
            "Server 11",
            "http://comixedproject.org:7171/opds",
            "admin@comixedproject.org",
            "my!password"
        ),
        Server(
            "2",
            "Server 12",
            "http://comixedproject.org:7171/opds",
            "admin@comixedproject.org",
            "my!password"
        ),
        Server(
            "3",
            "Server 13",
            "http://comixedproject.org:7171/opds",
            "admin@comixedproject.org",
            "my!password"
        ),
        Server(
            "4",
            "Server 14",
            "http://comixedproject.org:7171/opds",
            "admin@comixedproject.org",
            "my!password"
        ),
        Server(
            "5",
            "Server 15",
            "http://comixedproject.org:7171/opds",
            "admin@comixedproject.org",
            "my!password"
        )
    )

    val serverList: List<Server>
        get() = _serverList

    fun createServer(id: String, name: String, url: String, username: String, password: String) {
        _serverList.add(Server(id, name, url, username, password))
    }

    fun updateServer(id: String, name: String, url: String, username: String, password: String) {
        _serverList.removeAll { it.id === id }
        _serverList.add(Server(id, name, url, username, password))
    }

    fun deleteServer(id: String) {
        _serverList.removeAll { it.id === id }
    }
}