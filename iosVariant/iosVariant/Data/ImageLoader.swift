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

private let TAG = "ImageLoader"

@MainActor
class ImageLoader: ObservableObject {
    private var comicFilename: String = ""
    private var pageFilename: String = ""

    @Published var image: UIImage? = nil

    init(comicBook: ComicBook) {
        self.comicFilename = comicBook.path

        if comicBook.pages.count > 0 {
            self.pageFilename = (comicBook.pages[0] as! ComicPage).filename
            doLoadCover()
        }
    }

    init(comicFilename: String, pageFileanme: String) {
        self.comicFilename = comicFilename
        self.pageFilename = pageFileanme
        doLoadPage()
    }

    private func doLoadCover() {
        Task {
            Log().debug(
                tag: TAG,
                message:
                    "Loading cover image: \(self.comicFilename):\(self.pageFilename)"
            )
            let imageData = try await ArchiveAPI().loadCover(
                comicFilename: self.comicFilename,
                pageFilename: self.pageFilename
            )

            if imageData != nil {
                self.image = imageData!.toUIImage()
            }
        }

    }

    private func doLoadPage() {
        Task {
            Log().debug(
                tag: TAG,
                message:
                    "Loading page image: \(self.comicFilename):\(self.pageFilename)"
            )
            let imageData = try await ArchiveAPI().loadPage(
                comicFilename: self.comicFilename,
                pageFilename: self.pageFilename
            )

            if imageData != nil {
                self.image = imageData!.toUIImage()
            }
        }

    }
}
