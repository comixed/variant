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

class ImageLoader: ObservableObject {
    private var comicBook: ComicBook
    private var pageFilename: String = ""

    @Published var image: UIImage? = nil

    init(comicBook: ComicBook) {
        self.comicBook = comicBook

        if self.comicBook.pages.count > 0 {
            self.pageFilename = (self.comicBook.pages[0] as! ComicPage).filename
            doLoadPage()
        }
    }

    private func doLoadPage() {
        Task {
            Log().debug(
                tag: TAG,
                message:
                    "Loading cover image: \(comicBook.path):\(self.pageFilename)"
            )
            let imageData = try await ArchiveAPI().loadPage(
                comicFilename: comicBook.path,
                pageFilename: self.pageFilename
            )

            if imageData != nil {
                self.image = imageData!.toUIImage()
            }
        }

    }
}
