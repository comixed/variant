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

import Foundation
import Variant

var SERVER_LIST = [
  Server(
    serverId: 1, name: "Server 1", url: "http://www.comixedproject.org:7171/opds/root.xml",
    username: "reader@comixedproject.org", password: "the!password"),
  Server(
    serverId: 2, name: "Server 2", url: "http://www.comixedproject.org:7171/opds/root.xml",
    username: "reader@comixedproject.org", password: "the!password"),
  Server(
    serverId: 3, name: "Server 3", url: "http://www.comixedproject.org:7171/opds/root.xml",
    username: "reader@comixedproject.org", password: "the!password"),
  Server(
    serverId: 4, name: "Server 4", url: "http://www.comixedproject.org:7171/opds/root.xml",
    username: "reader@comixedproject.org", password: "the!password"),
  Server(
    serverId: 5, name: "Server 5", url: "http://www.comixedproject.org:7171/opds/root.xml",
    username: "reader@comixedproject.org", password: "the!password"),
]

var SERVER_LINK_LIST = [
  ServerLink(
    serverLinkId: 101, serverId: 1, directory: "/", identifier: "foo", title: "Captain America",
    coverUrl: "http://www.comixedproject.org/cover.png",
    downloadLink: "http://www.comixedproject.org:7171/opds/", linkType: ServerLinkType.navigation,
    downloaded: false),
  ServerLink(
    serverLinkId: 102, serverId: 1, directory: "/", identifier: "foo", title: "Captain America",
    coverUrl: "http://www.comixedproject.org/cover.png",
    downloadLink: "http://www.comixedproject.org:7171/opds/", linkType: ServerLinkType.navigation,
    downloaded: false),
  ServerLink(
    serverLinkId: 103, serverId: 1, directory: "/", identifier: "foo", title: "Captain America",
    coverUrl: "http://www.comixedproject.org/cover.png",
    downloadLink: "http://www.comixedproject.org:7171/opds/", linkType: ServerLinkType.publication,
    downloaded: false),
  ServerLink(
    serverLinkId: 104, serverId: 1, directory: "/", identifier: "foo", title: "Captain America",
    coverUrl: "http://www.comixedproject.org/cover.png",
    downloadLink: "http://www.comixedproject.org:7171/opds/", linkType: ServerLinkType.publication,
    downloaded: false),
  ServerLink(
    serverLinkId: 105, serverId: 1, directory: "/", identifier: "foo", title: "Captain America",
    coverUrl: "http://www.comixedproject.org/cover.png",
    downloadLink: "http://www.comixedproject.org:7171/opds/", linkType: ServerLinkType.publication,
    downloaded: false),
]
