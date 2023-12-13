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

import app.cash.sqldelight.db.SqlDriver
import org.comixedproject.variant.VariantDb
import org.comixedproject.variant.db.ServerDb

/**
 * <code>DatabaseHelper</code> provides support for working with SqlDelight.
 *
 * @author Darryl L. Pierce
 */
class DatabaseHelper(sqlDriver: SqlDriver) {
    private val database: VariantDb = VariantDb(sqlDriver)

    fun loadAll(): List<ServerDb> = database.tableQueries.loadServers().executeAsList()

    fun save(
        id: String,
        name: String,
        url: String,
        username: String,
        password: String,
        serverColor: String
    ) {
        database.tableQueries.saveServer(id, name, url, username, password, serverColor)
    }

    fun update(
        id: String,
        name: String,
        url: String,
        username: String,
        password: String,
        serverColor: String
    ) {
        database.tableQueries.updateServer(name, url, username, password, serverColor, id)
    }

    fun delete(id: String) {
        database.tableQueries.deleteServer(id)
    }
}