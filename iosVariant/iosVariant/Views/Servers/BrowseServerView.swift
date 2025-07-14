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

private let TAG = "BrowseServerView"

struct BrowseServerView: View {
    let comicBookList: [ComicBook]
    let path: String
    let title: String
    let parentPath: String
    let directoryContents: [DirectoryEntry]
    let downloadingState: [DownloadingState]
    let loading: Bool
    let onLoadDirectory: (String, Bool) -> Void
    let onDownloadFile: (String, String) -> Void

    @State private var selected: DirectoryEntry?

    var displayableTitle: String {
        if title.isEmpty {
            return "[Root Directory]"
        }
        return title
    }

    var body: some View {
        NavigationStack {
            ZStack {
                List(directoryContents, id: \.id, selection: $selected) {
                    entry in
                    if entry.isDirectory {
                        DirectoryItemView(
                            entry: entry,
                            onLoadDirectory: { path in
                                Log().info(
                                    tag: TAG,
                                    message: "Loading path: \(path)"
                                )
                                onLoadDirectory(path, false)
                            }
                        )
                    } else {
                        FileItemView(
                            entry: entry,
                            comicBookFiles: comicBookList.map {
                                $0.filename
                            },
                            downloadingState: downloadingState,
                            onDownloadFile: onDownloadFile
                        )
                    }
                }
                .grayscale(loading ? 1 : 0)

                if loading {
                    ProgressView()
                }
            }

            .refreshable {
                if !loading {
                    Log().info(
                        tag: TAG,
                        message:
                            "Reloading path: \(path)"
                    )
                    onLoadDirectory(
                        path,
                        true
                    )
                }
            }
            .navigationTitle(displayableTitle)
            .toolbar {
                if parentPath != "" {
                    ToolbarItem(placement: .topBarLeading) {
                        Button {
                            Log().info(
                                tag: TAG,
                                message: "Loading parent: \(parentPath)"
                            )
                            onLoadDirectory(parentPath, false)
                        } label: {
                            Image(systemName: "arrow.backward")
                        }
                    }
                }
            }
        }
    }
}

#Preview("directories") {
    BrowseServerView(
        comicBookList: COMIC_BOOK_LIST,
        path: "/reader/v1",
        title: "",
        parentPath: "",
        directoryContents: DIRECTORY_LIST.filter { $0.isDirectory },
        downloadingState: [],
        loading: false,
        onLoadDirectory: { _, _ in },
        onDownloadFile: { _, _ in }
    )
}

#Preview("files") {
    BrowseServerView(
        comicBookList: COMIC_BOOK_LIST,
        path: "/reader/v1",
        title: "",
        parentPath: "",
        directoryContents: DIRECTORY_LIST.filter { $0.isDirectory == false },
        downloadingState: [],
        loading: false,
        onLoadDirectory: { _, _ in },
        onDownloadFile: { _, _ in }
    )
}

#Preview("loading") {
    BrowseServerView(
        comicBookList: COMIC_BOOK_LIST,
        path: "/reader/v1",
        title: "",
        parentPath: "",
        directoryContents: DIRECTORY_LIST.filter { $0.isDirectory },
        downloadingState: [],
        loading: true,
        onLoadDirectory: { _, _ in },
        onDownloadFile: { _, _ in }
    )
}
