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

public var DIRECTORY_LIST = [
    DirectoryEntry(
        id: 1,
        directoryId: "1",
        title: "All Comics",
        filename: "",
        fileSize: 0,
        path: "/api/v1/all",
        parent: "/api/v1/root",
        isDirectory: true,
        coverUrl: ""
    ),
    DirectoryEntry(
        id: 2,
        directoryId: "2",
        title: "Unread Comics",
        filename: "",
        fileSize: 0,
        path: "/api/v1/all?unread=true",
        parent: "/api/v1/root",
        isDirectory: true,
        coverUrl: ""
    ),
    DirectoryEntry(
        id: 3,
        directoryId: "3",
        title: "Collections",
        filename: "",
        fileSize: 0,
        path: "/api/v1/collections",
        parent: "/api/v1/root",
        isDirectory: true,
        coverUrl: ""
    ),
    DirectoryEntry(
        id: 4,
        directoryId: "4",
        title: "Reading Lists",
        filename: "",
        fileSize: 0,
        path: "/api/v1/lists/reading",
        parent: "/api/v1/root",
        isDirectory: true,
        coverUrl: ""
    ),
    DirectoryEntry(
        id: 6,
        directoryId: "5",
        title: "Smart Lists",
        filename: "",
        fileSize: 0,
        path: "/api/v1/lists/smart",
        parent: "/api/v1/root",
        isDirectory: true,
        coverUrl: ""
    ),
    DirectoryEntry(
        id: 11,
        directoryId: "11",
        title: "Amazing Spider-Man #75 (v2018) (No Cover Date).cbz",
        filename: "Amazing Spider-Man #75 (v2018) (No Cover Date).cbz",
        fileSize: 32 * Int64(BYTES_PER_MB),
        path: "/api/v1/lists/reading",
        parent: "/api/v1/root",
        isDirectory: false,
        coverUrl: ""
    ),
    DirectoryEntry(
        id: 12,
        directoryId: "12",
        title: "Amazing Spider-Man #6 (v2022) (Sep 2022).cbz",
        filename: "Amazing Spider-Man #6 (v2022) (Sep 2022).cbz",
        fileSize: 32 * Int64(BYTES_PER_MB),
        path: "/api/v1/lists/smart",
        parent: "/api/v1/root",
        isDirectory: false,
        coverUrl: ""
    ),
    DirectoryEntry(
        id: 13,
        directoryId: "13",
        title: "[unknown] V???? #? (unknown)",
        filename: "Unknown VUnknown #Unknown (Unknown).cbz",
        fileSize: 32 * Int64(BYTES_PER_MB),
        path: "/api/v1/lists/smart",
        parent: "/api/v1/root",
        isDirectory: false,
        coverUrl: ""
    ),
]

public var COMIC_BOOK_LIST = DIRECTORY_LIST.filter { !$0.isDirectory }.map {
    ComicBook(
        path: $0.path,
        filename: $0.filename,
        size: Int64(($0.filename.count * 1024)),
        lastModified: Int64(Date().timeIntervalSince1970),
        metadata: ComicBookMetadata(
            publisher: "Marvel",
            series: "The Amazing Spider-Man",
            volume: "1963",
            issueNumber: "183",
            coverDate: 0,
            title: "The Title",
            notes: "The Notes",
            summary: "The Summary",
            year: 1963,
            month: 5
        ),
        pages: []
    )
}
