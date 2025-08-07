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

private let TAG = "ReadingView"

struct ReadingView: View {
    @State private var currentPage = 0.0
    @State private var showPageOverlay = false

    let comicBook: ComicBook

    var onStopReading: () -> Void

    var comicFilename: String {
        return comicBook.path
    }

    var comicTitle: String {
        return comicBook.filename
    }

    var pageFilename: String {
        return (comicBook.pages[Int(currentPage)] as! ComicPage).filename
    }

    var title: String {
        return (comicBook.pages[Int(currentPage)] as! ComicPage).filename
    }

    var body: some View {
        PageNavigationView(
            comicFilename: comicFilename,
            comicTitle: comicTitle,
            pageFilename: pageFilename,
            title: title,
            pageNumber: $currentPage,
            totalPages: comicBook.pages.count,
            showPageOverlay: showPageOverlay,
            onStopReading: { onStopReading() },
            onToggleShowOverlay: { enable in
                Log().debug(
                    tag: TAG,
                    message: "Setting show overlay to \(enable)"
                )
                showPageOverlay = enable
            }
        )
    }
}

#Preview {
    ReadingView(comicBook: COMIC_BOOK_LIST[0], onStopReading: {})
}
