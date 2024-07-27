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

package org.comixedproject.variant.shared.model.server

/**
 * <code>ServerLink</code> represents a link to content within a specific server. The link type determines if the link is to a piece of content or to a directory on the server.
 *
 * <p>Links that are for navigation
 *
 * @author Darryl L. Pierce
 */
data class ServerLink(
    val serverLinkId: Long? = null,
    val serverId: Long,
    val directory: String,
    val identifier: String,
    val title: String? = null,
    val href: String,
    val linkType: ServerLinkType,
)
