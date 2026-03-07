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

import KMPObservableViewModelSwiftUI
import SwiftUI
import shared

private let TAG = "ServerView"

struct ServerView: View {
    let comicBookList: [ComicBook]
    let currentPath: String
    let filtering: Bool
    let filterText: String
    let title: String
    let parentPath: String
    let directoryContents: [DirectoryEntry]
    let downloadingState: [DownloadingState]
    let loading: Bool

    let onLoadDirectory: (String, Bool) -> Void
    let onToggleFilter: (Bool) -> Void
    let onUpdateFilterText: (String) -> Void
    let onDownloadFile: (String, String) -> Void

    var body: some View {
        BrowseServerView(
            comicBookList: comicBookList,
            path: currentPath,
            filtering: filtering,
            filterText: filterText,
            title: title,
            parentPath: parentPath,
            directoryContents: directoryContents,
            downloadingState: downloadingState,
            loading: loading,
            onLoadDirectory: onLoadDirectory,
            onToggleFilter: onToggleFilter,
            onUpdateFilterText: onUpdateFilterText,
            onDownloadFile: onDownloadFile
        )
    }
}

#Preview {
    ServerView(
        comicBookList: COMIC_BOOK_LIST,
        currentPath: "The current path",
        filtering: false,
        filterText: "",
        title: "The Title",
        parentPath: "The parent path",
        directoryContents: DIRECTORY_LIST,
        downloadingState: [],
        loading: false,
        onLoadDirectory: { _, _ in },
        onToggleFilter: { _ in },
        onUpdateFilterText: { _ in },
        onDownloadFile: { _, _ in }
    )
}
