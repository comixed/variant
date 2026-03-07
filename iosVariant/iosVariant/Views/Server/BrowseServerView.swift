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

import Combine
import KMPObservableViewModelSwiftUI
import SwiftUI
import shared

private let TAG = "BrowseServerView"

struct BrowseServerView: View {
    let comicBookList: [ComicBook]
    let path: String
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

    @State private var selected: DirectoryEntry?
    @State private var filterTextValue: String = ""

    init(
        comicBookList: [ComicBook],
        path: String,
        filtering: Bool,
        filterText: String,
        title: String,
        parentPath: String,
        directoryContents: [DirectoryEntry],
        downloadingState: [DownloadingState],
        loading: Bool,
        onLoadDirectory: @escaping (String, Bool) -> Void,
        onToggleFilter: @escaping (Bool) -> Void,
        onUpdateFilterText: @escaping (String) -> Void,
        onDownloadFile: @escaping (String, String) -> Void
    ) {
        self.comicBookList = comicBookList
        self.path = path
        self.filtering = filtering
        self.filterText = filterText
        self.title = title
        self.parentPath = parentPath
        self.directoryContents = directoryContents
        self.downloadingState = downloadingState
        self.loading = loading
        self.onLoadDirectory = onLoadDirectory
        self.onToggleFilter = onToggleFilter
        self.onUpdateFilterText = onUpdateFilterText
        self.onDownloadFile = onDownloadFile
        self.selected = selected
        self.filterTextValue = filterText
    }

    var displayableTitle: String {
        if title.isEmpty {
            return "[Root Directory]"
        }
        return title
    }

    var filteredContents: [DirectoryEntry] {
        return self.directoryContents.filter {
            !self.filtering || self.filterText.isEmpty
                || ($0.title.lowercased().contains(self.filterText.lowercased())
                    || $0.path.lowercased().contains(
                        self.filterText.lowercased()
                    ))
        }
    }

    var body: some View {
        NavigationStack {
            ZStack {
                List(filteredContents, id: \.id, selection: $selected) {
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
                ToolbarItem(placement: .topBarLeading) {
                    HStack {
                        if parentPath != "" {
                            Button {
                                Log().info(
                                    tag: TAG,
                                    message: "Loading parent: \(parentPath)"
                                )
                                onLoadDirectory(parentPath, false)
                            } label: {
                                Image("back")
                            }
                        }

                        if filtering {
                            TextField(
                                String(
                                    localized:
                                        "browse-server.label.filter-text",
                                    defaultValue: "Enter filter text...", table: "Translations"
                                ),
                                text: $filterTextValue
                            )
                            .disableAutocorrection(true)
                            .autocapitalization(.none)
                            .onReceive(Just(filterText)) { _ in
                                onUpdateFilterText(
                                    $filterTextValue.wrappedValue
                                )
                            }

                        }
                    }
                }

                ToolbarItem(placement: .topBarTrailing) {
                    Button {
                        onToggleFilter(!filtering)
                    } label: {
                        Image(
                            systemName: "line.3.horizontal.decrease.circle"
                        )
                    }
                }
            }
        }
    }
}

#Preview("directories") {
    BrowseServerView(
        comicBookList: COMIC_BOOK_LIST,
        path: "/reader/v1/publishers",
        filtering: false,
        filterText: "",
        title: "",
        parentPath: "/reader/v1",
        directoryContents: DIRECTORY_LIST.filter { $0.isDirectory },
        downloadingState: [],
        loading: false,
        onLoadDirectory: { _, _ in },
        onToggleFilter: { _ in },
        onUpdateFilterText: { _ in },
        onDownloadFile: { _, _ in }
    )
}

#Preview("files") {
    BrowseServerView(
        comicBookList: COMIC_BOOK_LIST,
        path: "/reader/v1",
        filtering: false,
        filterText: "",
        title: "",
        parentPath: "",
        directoryContents: DIRECTORY_LIST.filter { $0.isDirectory == false },
        downloadingState: [],
        loading: false,
        onLoadDirectory: { _, _ in },
        onToggleFilter: { _ in },
        onUpdateFilterText: { _ in },
        onDownloadFile: { _, _ in }
    )
}

#Preview("loading") {
    BrowseServerView(
        comicBookList: COMIC_BOOK_LIST,
        path: "/reader/v1",
        filtering: false,
        filterText: "",
        title: "",
        parentPath: "",
        directoryContents: DIRECTORY_LIST.filter { $0.isDirectory },
        downloadingState: [],
        loading: true,
        onLoadDirectory: { _, _ in },
        onToggleFilter: { _ in },
        onUpdateFilterText: { _ in },
        onDownloadFile: { _, _ in }
    )
}

#Preview("filtering") {
    BrowseServerView(
        comicBookList: COMIC_BOOK_LIST,
        path: "/reader/v1",
        filtering: true,
        filterText: DIRECTORY_LIST.filter { $0.isDirectory }[0].title,
        title: "",
        parentPath: "",
        directoryContents: DIRECTORY_LIST.filter { $0.isDirectory },
        downloadingState: [],
        loading: true,
        onLoadDirectory: { _, _ in },
        onToggleFilter: { _ in },
        onUpdateFilterText: { _ in },
        onDownloadFile: { _, _ in }
    )
}
