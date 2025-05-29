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

package org.comixedproject.variant.android

import org.comixedproject.variant.model.Server
import org.comixedproject.variant.model.library.DirectoryEntry

val SERVER_LIST = listOf(
    Server(
        serverId = 1,
        name = "Home Server",
        url = "http://www.comixedproject.org:7171/opds/root.xml",
        username = "reader@comixedproject.org",
        password = "my!password"
    ),
    Server(
        serverId = 2,
        name = "Shared Server",
        url = "http://www.comixedproject.org:7171/opds/root.xml",
        username = "reader@comixedproject.org",
        password = "my!password"
    ),
    Server(
        serverId = 3,
        name = "Buddy's Server",
        url = "http://www.comixedproject.org:7171/opds/root.xml",
        username = "reader@comixedproject.org",
        password = "my!password"
    ),
    Server(
        serverId = 4,
        name = "Testing Server",
        url = "http://www.comixedproject.org:7171/opds/root.xml",
        username = "reader@comixedproject.org",
        password = "my!password"
    ),
    Server(
        serverId = 5,
        name = "Some Other Server",
        url = "http://www.comixedproject.org:7171/opds/root.xml",
        username = "reader@comixedproject.org",
        password = "my!password"
    ),
)

val DIRECTORY_LIST = listOf(
    DirectoryEntry(
        1,
        "1",
        1,
        "All Comics",
        "/api/v1/all",
        "/api/v1/root",
        "",
        true,
        null
    ),
    DirectoryEntry(
        2,
        "2",
        1,
        "Unread Comics",
        "/api/v1/all?unread=true",
        "/api/v1/root",
        "",
        true,
        null
    ),
    DirectoryEntry(
        3,
        "3",
        1,
        "Collections",
        "/api/v1/collections",
        "/api/v1/root",
        "",
        true,
        null
    ),
    DirectoryEntry(
        4,
        "4",
        1,
        "Reading Lists",
        "/api/v1/lists/reading",
        "/api/v1/root",
        "",
        true,
        null
    ),
    DirectoryEntry(
        5,
        "5",
        1,
        "Smart Lists",
        "/api/v1/lists/smart",
        "/api/v1/root",
        "",
        true,
        null
    ),
    DirectoryEntry(
        11,
        "11",
        1,
        "The Amazing Spider-Man V2018 #75 (unknown)",
        "Amazing Spider-Man #75 (v2018) (No Cover Date).cbz",
        "/api/v1/lists/reading",
        "/api/v1/root",
        false,
        ""
    ),
    DirectoryEntry(
        12,
        "12",
        1,
        "The Amazing Spider-Man V2022 #75 (unknown)",
        "Amazing Spider-Man #6 (v2022) (Sep 2022).cbz",
        "/api/v1/lists/smart",
        "/api/v1/root",
        false,
        ""
    ),
    DirectoryEntry(
        13,
        "13",
        1,
        "[unknown] V???? #? (unknown)",
        "Unknown VUnknown #Unknown (Unknown).cbz",
        "/api/v1/lists/smart",
        "/api/v1/root",
        false,
        ""
    )
)
