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

import org.comixedproject.variant.db.AcquisitionLinksDb
import org.comixedproject.variant.shared.model.server.AcquisitionLink

class LinkRepository(private val databaseHelper: DatabaseHelper) {
    fun loadAllLinks() = databaseHelper.loadAllLinks().map(AcquisitionLinksDb::map)

    fun linksForParent(serverId: String, directory: String) =
        databaseHelper.loadLinks(serverId, directory).map(AcquisitionLinksDb::map)

    fun saveLinksForServer(
        serverId: String,
        directory: String,
        acquisitionLinks: List<AcquisitionLink>
    ) {
        databaseHelper.saveLinksForServer(serverId, directory, acquisitionLinks);
    }
}

fun AcquisitionLinksDb.map() = AcquisitionLink(
    id = this.id,
    serverId = this.serverId,
    linkId = this.linkId,
    directory = this.directory,
    link = this.link,
    title = this.title,
    thumbnailURL = this.thumbnailURL
)
