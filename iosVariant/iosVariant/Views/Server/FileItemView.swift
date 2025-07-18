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

import SwiftUI
import shared

private let TAG = "FileItemView"

struct FileItemView: View {
    let entry: DirectoryEntry
    let comicBookFiles: [String]
    let downloadingState: [DownloadingState]

    var onDownloadFile: (String, String) -> Void

    var downloaded: Bool {
        return comicBookFiles.contains(entry.filename)
    }

    var downloading: Bool {
        return downloadingState.map { $0.path }.contains(entry.path)
    }

    var downloadProgress: Double {
        let state = downloadingState.filter { $0.path == entry.path }.first!
        if state.total > 0 {
            return Double(state.received) / Double(state.total)
        } else {
            return 0.0
        }
    }

    var fileSize: String {
        return
            "\(String(format: "%.1f", Double(entry.fileSize) / BYTES_PER_MB))"
    }

    var body: some View {
        HStack {
            if downloaded {
                Button {

                } label: {
                    Image("downloaded_file")
                        .resizable()
                        .scaledToFit()
                        .frame(width: 32)
                }
            } else if downloading {
                Button {

                } label: {
                    Image("downloading_file")
                        .resizable()
                        .scaledToFit()
                        .frame(width: 32)
                }
            } else {
                Button {
                    Log().info(
                        tag: TAG,
                        message: "Downloading file: \(entry.path)"
                    )
                    onDownloadFile(entry.path, entry.filename)
                } label: {
                    Image("download_file")
                        .resizable()
                        .scaledToFit()
                        .frame(width: 32)
                }
            }

            VStack(alignment: .leading) {
                Text(entry.title)
                    .font(.headline)

                if downloading {
                    HStack {
                        Text("\(fileSize) MB")
                        ProgressView(value: downloadProgress)
                    }
                } else {
                    Text(entry.filename)
                        .font(.subheadline)
                }
            }
        }
    }
}

#Preview("default") {
    FileItemView(
        entry: DIRECTORY_LIST.filter { $0.isDirectory == false }.first!,
        comicBookFiles: [],
        downloadingState: [],
        onDownloadFile: { _, _ in }
    )
}

#Preview("already downloaded") {
    FileItemView(
        entry: DIRECTORY_LIST.filter { $0.isDirectory == false }.first!,
        comicBookFiles: [
            DIRECTORY_LIST.filter { $0.isDirectory == false }.first!.filename
        ],
        downloadingState: [],
        onDownloadFile: { _, _ in }
    )
}

#Preview("downloading") {
    FileItemView(
        entry: DIRECTORY_LIST.filter { $0.isDirectory == false }.first!,
        comicBookFiles: [],
        downloadingState: [
            DownloadingState(
                path: DIRECTORY_LIST.filter { $0.isDirectory == false }.first!
                    .path,
                filename: DIRECTORY_LIST.filter { $0.isDirectory == false }
                    .first!
                    .path,
                received: 50,
                total: 100
            )
        ],
        onDownloadFile: { _, _ in }
    )
}
