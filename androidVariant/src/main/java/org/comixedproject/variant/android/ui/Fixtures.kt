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

package org.comixedproject.variant.android.ui

import org.comixedproject.variant.android.ui.server.DIRECTORY
import org.comixedproject.variant.android.ui.server.SERVER_ID
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.model.server.ServerLink
import org.comixedproject.variant.shared.model.server.ServerLinkType

val SERVER_LIST = listOf(
    Server(1L, "Server 1", "http://server1.org:7171/opds", "admin@comixedproject.org", "p455w0rD"),
    Server(2L, "Server 2", "http://server2.org:7171/opds", "admin@comixedproject.org", "p455w0rD"),
    Server(3L, "Server 3", "http://server3.org:7171/opds", "admin@comixedproject.org", "p455w0rD"),
    Server(4L, "Server 4", "http://server4.org:7171/opds", "admin@comixedproject.org", "p455w0rD"),
    Server(5L, "Server 5", "http://server5.org:7171/opds", "admin@comixedproject.org", "p455w0rD"),
)

val SERVER_NAVIGATION_LINK = ServerLink(
    100L,
    SERVER_ID,
    DIRECTORY,
    "/opds/link1",
    "First Entry",
    "",
    "",
    ServerLinkType.NAVIGATION,
)

val SERVER_PUBLICATION_LINK = ServerLink(
    101L,
    SERVER_ID,
    DIRECTORY,
    "/opds/link1",
    "Second Entry",
    "",
    "",
    ServerLinkType.NAVIGATION,
)

val SERVER_LINK_LIST = listOf(
    SERVER_NAVIGATION_LINK,
    SERVER_PUBLICATION_LINK,
    ServerLink(
        102L,
        SERVER_ID,
        DIRECTORY,
        "/opds/link1",
        "Third Entry",
        "",
        "",
        ServerLinkType.NAVIGATION,
    ),
    ServerLink(
        103L,
        SERVER_ID,
        DIRECTORY,
        "/opds/link1",
        "Fourth Entry",
        "",
        "",
        ServerLinkType.NAVIGATION,
    ),
    ServerLink(
        104L,
        SERVER_ID,
        DIRECTORY,
        "/opds/link1",
        "Fifth Entry",
        "",
        "",
        ServerLinkType.NAVIGATION,
    ),
)