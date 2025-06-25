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
    @EnvironmentViewModel var variantViewModel: VariantViewModel

    var body: some View {
        BrowseServerView(
            path: self.variantViewModel.currentPath,
            title: self.variantViewModel.title,
            parentPath: self.variantViewModel.parentPath,
            directoryContents: self.variantViewModel.directoryContents,
            downloadingState: self.variantViewModel.downloadingState
                as! [DownloadingState],
            isRefreshing: self.variantViewModel.loading,
            onLoadDirectory: { path, reload in
                Log().debug(
                    tag: TAG,
                    message: "Loading path: \(path) reload=\(reload)"
                )
                Task {
                    try await self.variantViewModel.loadDirectory(
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

                    self.variantViewModel.downloadFile(
                        path: path,
                        filename: filename
                    )
                }
            }
        )
    }
}

#Preview {
    ServerView()
}
