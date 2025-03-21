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

package org.comixedproject.variant.android.model

import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.model.server.ServerLink
import org.comixedproject.variant.shared.model.server.ServerLinkType

val SERVER_LIST = listOf(
    Server(
        serverId = 1L,
        name = "Server 1",
        url = "http://www.comixedproject.org:7171/opds/root.xml",
        username = "admin@comixedproject.org",
        password = "test4echo"
    ),
    Server(
        serverId = 2L,
        name = "Server 2",
        url = "http://www.comixedproject.org:7171/opds/root.xml",
        username = "admin@comixedproject.org",
        password = "test4echo"
    ),
    Server(
        serverId = 3L,
        name = "Server 3",
        url = "http://www.comixedproject.org:7171/opds/root.xml",
        username = "admin@comixedproject.org",
        password = "test4echo"
    ),
    Server(
        serverId = 4L,
        name = "Server 4",
        url = "http://www.comixedproject.org:7171/opds/root.xml",
        username = "admin@comixedproject.org",
        password = "test4echo"
    ),
    Server(
        serverId = 5L,
        name = "Server 5",
        url = "http://www.comixedproject.org:7171/opds/root.xml",
        username = "admin@comixedproject.org",
        password = "test4echo"
    ),
);

val SERVER_LINK_LIST = listOf(
    ServerLink(
        serverLinkId = 101L,
        serverId = 1L,
        directory = "/",
        identifier = "foo",
        title = "Captain America",
        coverUrl = "http://www.comixedproject.org/cover.png",
        downloadLink = "http://www.comixedproject.org:7171/opds/",
        linkType = ServerLinkType.NAVIGATION
    ),
    ServerLink(
        serverLinkId = 102L,
        serverId = 1L,
        directory = "/",
        identifier = "foo",
        title = "Daredevil",
        coverUrl = "http://www.comixedproject.org/cover.png",
        downloadLink = "http://www.comixedproject.org:7171/opds/",
        linkType = ServerLinkType.NAVIGATION
    ),
    ServerLink(
        serverLinkId = 103L,
        serverId = 1L,
        directory = "/",
        identifier = "foo",
        title = "The Avengers",
        coverUrl = "http://www.comixedproject.org/cover.png",
        downloadLink = "http://www.comixedproject.org:7171/opds/",
        linkType = ServerLinkType.NAVIGATION
    ),
    ServerLink(
        serverLinkId = 104L,
        serverId = 1L,
        directory = "/",
        identifier = "foo",
        title = "JSA",
        coverUrl = "http://www.comixedproject.org/cover.png",
        downloadLink = "http://www.comixedproject.org:7171/opds/",
        linkType = ServerLinkType.NAVIGATION
    ),
    ServerLink(
        serverLinkId = 105L,
        serverId = 1L,
        directory = "/",
        identifier = "foo",
        title = "The Green Lantern Corps",
        coverUrl = "http://www.comixedproject.org/cover.png",
        downloadLink = "http://www.comixedproject.org:7171/opds/",
        linkType = ServerLinkType.NAVIGATION
    ),
    ServerLink(
        serverLinkId = 111L,
        serverId = 1L,
        directory = "/",
        identifier = "foo",
        title = "Detective Comics #27",
        coverUrl = "http://www.comixedproject.org/cover.png",
        downloadLink = "http://www.comixedproject.org:7171/opds/",
        linkType = ServerLinkType.PUBLICATION
    ),
    ServerLink(
        serverLinkId = 112L,
        serverId = 1L,
        directory = "/",
        identifier = "foo",
        title = "Detective Comics #28",
        coverUrl = "http://www.comixedproject.org/cover.png",
        downloadLink = "http://www.comixedproject.org:7171/opds/",
        linkType = ServerLinkType.PUBLICATION
    ),
    ServerLink(
        serverLinkId = 113L,
        serverId = 1L,
        directory = "/",
        identifier = "foo",
        title = "Detective Comics #29",
        coverUrl = "http://www.comixedproject.org/cover.png",
        downloadLink = "http://www.comixedproject.org:7171/opds/",
        linkType = ServerLinkType.PUBLICATION
    ),
    ServerLink(
        serverLinkId = 114L,
        serverId = 1L,
        directory = "/",
        identifier = "foo",
        title = "Detective Comics #30",
        coverUrl = "http://www.comixedproject.org/cover.png",
        downloadLink = "http://www.comixedproject.org:7171/opds/",
        linkType = ServerLinkType.PUBLICATION
    ),
    ServerLink(
        serverLinkId = 115L,
        serverId = 1L,
        directory = "/",
        identifier = "foo",
        title = "Detective Comics #31",
        coverUrl = "http://www.comixedproject.org/cover.png",
        downloadLink = "http://www.comixedproject.org:7171/opds/",
        linkType = ServerLinkType.PUBLICATION
    ),
)