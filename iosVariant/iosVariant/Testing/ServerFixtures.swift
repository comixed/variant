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

import Variant

public var SERVER_LIST = [
    Server(
        serverId: 1,
        name: "Home Server",
        url: "http://www.comixedproject.org:7171/opds/root.xml",
        username: "reader@comixedproject.org",
        password: "my!password"
    ),
    Server(
        serverId: 2,
        name: "Shared Server",
        url: "http://www.comixedproject.org:7171/opds/root.xml",
        username: "reader@comixedproject.org",
        password: "my!password"
    ),
    Server(
        serverId: 3,
        name: "Buddy's Server",
        url: "http://www.comixedproject.org:7171/opds/root.xml",
        username: "reader@comixedproject.org",
        password: "my!password"
    ),
    Server(
        serverId: 4,
        name: "Testing Server",
        url: "http://www.comixedproject.org:7171/opds/root.xml",
        username: "reader@comixedproject.org",
        password: "my!password"
    ),
    Server(
        serverId: 5,
        name: "Some Other Server",
        url: "http://www.comixedproject.org:7171/opds/root.xml",
        username: "reader@comixedproject.org",
        password: "my!password"
    ),
]

public var DIRECTORY_LIST = [
    DirectoryEntry(
        id: 1,
        directoryId: "1",
        serverId: 1,
        title: "All Comics",
        filename: "",
        path: "/api/v1/all",
        parent: "/api/v1/root",
        isDirectory: true,
        coverUrl: ""
    ),
    DirectoryEntry(
        id: 2,
        directoryId: "2",
        serverId: 1,
        title: "Unread Comics",
        filename: "",
        path: "/api/v1/all?unread=true",
        parent: "/api/v1/root",
        isDirectory: true,
        coverUrl: ""
    ),
    DirectoryEntry(
        id: 3,
        directoryId: "3",
        serverId: 1,
        title: "Collections",
        filename: "",
        path: "/api/v1/collections",
        parent: "/api/v1/root",
        isDirectory: true,
        coverUrl: ""
    ),
    DirectoryEntry(
        id: 4,
        directoryId: "4",
        serverId: 1,
        title: "Reading Lists",
        filename: "",
        path: "/api/v1/lists/reading",
        parent: "/api/v1/root",
        isDirectory: true,
        coverUrl: ""
    ),
    DirectoryEntry(
        id: 6,
        directoryId: "5",
        serverId: 1,
        title: "Smart Lists",
        filename: "",
        path: "/api/v1/lists/smart",
        parent: "/api/v1/root",
        isDirectory: true,
        coverUrl: ""
    ),
    DirectoryEntry(
        id: 11,
        directoryId: "11",
        serverId: 1,
        title: "Amazing Spider-Man #75 (v2018) (No Cover Date).cbz",
        filename: "Amazing Spider-Man #75 (v2018) (No Cover Date).cbz",
        path: "/api/v1/lists/reading",
        parent: "/api/v1/root",
        isDirectory: false,
        coverUrl: ""
    ),
    DirectoryEntry(
        id: 12,
        directoryId: "12",
        serverId: 1,
        title: "Amazing Spider-Man #6 (v2022) (Sep 2022).cbz",
        filename: "Amazing Spider-Man #6 (v2022) (Sep 2022).cbz",
        path: "/api/v1/lists/smart",
        parent: "/api/v1/root",
        isDirectory: false,
        coverUrl: ""
    ),
    DirectoryEntry(
        id: 13,
        directoryId: "13",
        serverId: 1,
        title: "[unknown] V???? #? (unknown)",
        filename: "Unknown VUnknown #Unknown (Unknown).cbz",
        path: "/api/v1/lists/smart",
        parent: "/api/v1/root",
        isDirectory: false,
        coverUrl: ""
    ),
]
