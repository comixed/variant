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
import Variant

private let TAG = "ServerView"

struct ServerView: View {
    @StateViewModel var variantViewModel: VariantViewModel = Koin.instance.get()

    var downloadingState: [DownloadingState] {
        return []
    }

    var body: some View {
        VStack {
            if self.variantViewModel.editing {
                EditServerView(
                    server: self.variantViewModel.editingServer!,
                    onSaveChanges: { server in
                        Task {
                            try await self.variantViewModel.saveServer(
                                server: server
                            )
                        }
                        self.variantViewModel.editServer(server: nil)
                    },
                    onCancelChanges: {
                        self.variantViewModel.editServer(server: nil)
                    }
                )
            } else if self.variantViewModel.browsing {
                BrowseServerView(
                    server: self.variantViewModel.browsingServer!,
                    path: self.variantViewModel.currentPath,
                    title: self.variantViewModel.title,
                    parentPath: self.variantViewModel.parentPath,
                    directoryContents: self.variantViewModel.directoryContents,
                    downloadingState: downloadingState,
                    isRefreshing: self.variantViewModel.loading,
                    onLoadDirectory: { path, reload in
                        Log().debug(
                            tag: TAG,
                            message: "Loading path: \(path) reload=\(reload)"
                        )
                        Task {
                            try await self.variantViewModel.loadDirectory(
                                server:
                                    self.variantViewModel.browsingServer!,
                                path: path,
                                reload: reload
                            )
                        }
                    },
                    onDownloadFile: { path, filename in
                        Task {
                            Log().debug(
                                tag: TAG,
                                message: "Downloading file: \(filename)"
                            )

                            try await self.variantViewModel.downloadFile(
                                server: self.variantViewModel.browsingServer!,
                                path: path,
                                filename: filename
                            )
                        }
                    },
                    onStopBrowsing: {
                        Log().debug(tag: TAG, message: "Stopping browsing")
                        Task {
                            try await self.variantViewModel.stopBrowsing()
                        }
                    }
                )
            } else {
                ServerListView(
                    serverList: self.variantViewModel.serverList,
                    onEditServer: { server in
                        Log().debug(
                            tag: TAG,
                            message: "Editing server: \(server.name)"
                        )
                        self.variantViewModel.editServer(server: server)
                    },
                    onDeleteServer: { _ in },
                    onBrowseServer: { server in
                        Log().info(
                            tag: TAG,
                            message: "Starting to browse server: \(server.name)"
                        )
                        Task {
                            try await self.variantViewModel.loadDirectory(
                                server: server,
                                path: ReaderAPIKt.READER_ROOT,
                                reload: false
                            )
                        }
                    }
                )
            }
        }
    }
}

#Preview {
    ServerView()
}
