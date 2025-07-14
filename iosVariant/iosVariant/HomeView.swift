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

private let TAG = "HomeView"

struct HomeView: View {
    @EnvironmentViewModel var variantViewModel: VariantViewModel
    @State var currentDestination: AppDestination = .comics

    var body: some View {
        TabView(selection: $currentDestination) {
            ComicBooksView(
                comicBookList: variantViewModel.comicBookList,
                selectionMode: variantViewModel.selectionMode,
                selectionList: variantViewModel.selectionList,
                onSetSelectionMode: { enable in
                    Log().info(
                        tag: TAG,
                        message: "Setting selection mode: \(enable)"
                    )
                    variantViewModel.setSelectMode(enable: enable)
                },
                onComicClicked: { comicBook in
                    if variantViewModel.selectionMode {
                        Log().info(
                            tag: TAG,
                            message:
                                "Toggling comic book select: \(comicBook.path)"
                        )
                        variantViewModel.updateSelectionList(
                            filename: comicBook.path
                        )
                    } else {
                        Log().info(
                            tag: TAG,
                            message: "Reading comic book: \(comicBook.filename)"
                        )
                        variantViewModel.readComicBook(comicBook: comicBook)
                        currentDestination = .comics
                    }
                },
                onDeleteComics: {
                    Log().info(
                        tag: TAG,
                        message:
                            "Deleting \(variantViewModel.selectionList.count) comic book(s)"
                    )
                    Task {
                        try await variantViewModel.deleteSelections()
                    }
                }
            )
            .tag(AppDestination.comics)
            .tabItem {
                Label(
                    String(
                        localized: "destination.comics.label",
                        defaultValue: "Comics"
                    ),
                    systemImage: "book.fill"
                )
            }

            ServerView(
                comicBookList: self.variantViewModel.comicBookList,
                currentPath: self.variantViewModel.browsingState.currentPath,
                title: self.variantViewModel.browsingState.title,
                parentPath: self.variantViewModel.browsingState.parentPath,
                directoryContents: self.variantViewModel.browsingState.contents,
                downloadingState: self.variantViewModel.browsingState
                    .downloadingState,
                loading: self.variantViewModel.loading,
                onLoadDirectory: { path, reload in
                    Log().debug(
                        tag: TAG,
                        message: "Loading path: \(path) reload=\(reload)"
                    )
                    self.variantViewModel.loadDirectory(
                        path: path,
                        reload: reload
                    )
                },
                onDownloadFile: { path, filename in
                    Task {
                        Log().debug(
                            tag: TAG,
                            message: "Downloading file: \(filename)"
                        )

                        self.variantViewModel.downloadFile(
                            path: path,
                            filename: filename
                        )
                    }
                }
            )
            .tag(AppDestination.browseServer)
            .tabItem {
                Label(
                    String(
                        localized: "destination.browse-server.label",
                        defaultValue: "Browse"
                    ),
                    systemImage: "person.crop.circle.fill"
                )
            }

            SettingsView(
                address: variantViewModel.address,
                username: variantViewModel.username,
                password: variantViewModel.password,
                onSaveChanges: { address, username, password in
                    Log().debug(
                        tag: TAG,
                        message:
                            "Saving server settings: \(address), \(username), \(password)"
                    )
                    variantViewModel.address = address
                    variantViewModel.username = username
                    variantViewModel.password = password
                }
            )
            .tag(AppDestination.settings)
            .tabItem {
                Label(
                    String(
                        localized: "destination.settings.label",
                        defaultValue: "Settings"
                    ),
                    systemImage: "gearshape.fill"
                )
            }
        }
        .edgesIgnoringSafeArea(.bottom)
        .onChange(of: currentDestination) { tab in
            if tab == .browseServer {
                Task {
                    try await variantViewModel.loadDirectory(
                        path: variantViewModel.browsingState.currentPath,
                        reload: false
                    )
                }
            }
        }
    }
}
