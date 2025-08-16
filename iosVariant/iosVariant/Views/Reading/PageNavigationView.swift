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

private let TAG = "PageNavigationView"

struct PageNavigationView: View {
    @ObservedObject var imageLoader: ImageLoader
    @State var paageFilename = ""
    @State var currentPage = 0.0
    @State var showPageOverlay = false

    let comicBook: ComicBook
    var totalPages = 0
    var onStopReading: () -> Void

    var pageFilename: String {
        return (self.comicBook.pages[Int(currentPage)] as! ComicPage).filename
    }

    init(
        comicBook: ComicBook,
        onStopReading: @escaping () -> Void
    ) {
        self.comicBook = comicBook
        self.totalPages = comicBook.pages.count
        self.onStopReading = onStopReading

        self.imageLoader = ImageLoader(
            comicFilename: comicBook.path,
            pageFileanme: (comicBook.pages[Int(0)] as! ComicPage).filename
        )
    }

    var maxPage: Int {
        return self.comicBook.pages.count - 1
    }

    func loadPage() {
        let filename =
            (self.comicBook.pages[Int(self.currentPage)] as! ComicPage).filename
        self.imageLoader.loadPage(filename: filename)
    }

    var body: some View {
        NavigationStack {
            ScrollView {
                VStack(alignment: .leading) {
                    if showPageOverlay {
                        VStack(alignment: .center) {
                            Text(self.comicBook.filename).font(.headline).frame(
                                maxWidth: .infinity
                            )
                            Text(pageFilename).font(.subheadline).frame(
                                maxWidth: .infinity
                            )
                        }
                        HStack(alignment: .center) {
                            Button {
                                self.currentPage = self.currentPage - 1
                                Log().info(
                                    tag: TAG,
                                    message:
                                        "Going back to page \(self.currentPage) of \(self.totalPages)"
                                )
                                loadPage()
                            } label: {
                                Image("previous_page")
                            }
                            .disabled(self.currentPage == 0)

                            Slider(
                                value: $currentPage,
                                in: 0...Double(maxPage),
                                step: 1,
                                onEditingChanged: { editing in
                                    if !editing {
                                        Log().debug(
                                            tag: TAG,
                                            message:
                                                "Going to page \(self.currentPage)"
                                        )
                                        loadPage()
                                    }
                                }
                            )

                            Button {
                                self.currentPage = self.currentPage + 1
                                Log().info(
                                    tag: TAG,
                                    message:
                                        "Going forward to page \(self.currentPage) of \(self.totalPages)"
                                )
                                loadPage()
                            } label: {
                                Image("next_page")
                            }
                            .disabled(Int(currentPage) == (self.totalPages - 1))
                        }
                    }

                    if imageLoader.image != nil {
                        Image(uiImage: imageLoader.image!)
                            .resizable()
                            .aspectRatio(contentMode: .fill)
                            .onTapGesture {
                                Log().debug(
                                    tag: TAG,
                                    message: "Comic page tapped"
                                )
                                self.showPageOverlay = !self.showPageOverlay
                            }
                    } else {
                        Image(systemName: "placeholdertext.fill")
                            .resizable()
                            .aspectRatio(contentMode: .fill)
                    }
                }
                .toolbar {
                    if showPageOverlay {
                        ToolbarItem(placement: .topBarLeading) {
                            Button("Close") {
                                Log().info(
                                    tag: TAG,
                                    message: "Closing comic book"
                                )
                                onStopReading()
                            }
                        }
                    }
                }
            }
        }
    }
}

#Preview("normal") {
    PageNavigationView(
        comicBook: COMIC_BOOK_LIST[0],
        onStopReading: {}
    )
}

#Preview("withOverlay") {
    PageNavigationView(
        comicBook: COMIC_BOOK_LIST[0],
        onStopReading: {}
    )
}
