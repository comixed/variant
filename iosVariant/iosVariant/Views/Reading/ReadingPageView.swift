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

private let TAG = "ReadingPageView"

struct ReadingPageView: View {
    @ObservedObject var imageLoader: ImageLoader
    @Binding var pageNumber: Double

    let comicFilename: String
    let pageFilename: String
    let title: String
    let totalPages: Double

    var onStopReading: () -> Void

    init(
        comicFilename: String,
        pageFilename: String,
        title: String,
        pageNumber: Binding<Double>,
        totalPages: Int,
        onStopReading: @escaping () -> Void
    ) {
        self.comicFilename = comicFilename
        self.pageFilename = pageFilename
        self.title = title
        self._pageNumber = pageNumber
        self.totalPages = Double(totalPages)
        self.onStopReading = onStopReading

        self.imageLoader = ImageLoader(
            comicFilename: self.comicFilename,
            pageFileanme: self.pageFilename
        )
    }

    var maxPage: Double {
        return self.totalPages - 1
    }

    var body: some View {
        NavigationStack {
            ScrollView {
                VStack(alignment: .leading) {
                    if imageLoader.image != nil {
                        Image(uiImage: imageLoader.image!)
                            .resizable()
                            .aspectRatio(contentMode: .fill)
                    } else {
                        Image(systemName: "placeholdertext.fill")
                            .resizable()
                            .aspectRatio(contentMode: .fill)
                    }

                    HStack(alignment: .center) {
                        Button {
                            Log().info(
                                tag: TAG,
                                message: "Going to previous page"
                            )
                            self.pageNumber = self.pageNumber - 1
                        } label: {
                            Image("previous_page")
                        }
                        .disabled(pageNumber == 0)

                        Slider(
                            value: $pageNumber,
                            in: 0...maxPage,
                            step: 1,
                            onEditingChanged: { editing in
                                if !editing {
                                    Log().debug(
                                        tag: TAG,
                                        message: "Going to page \(pageNumber)"
                                    )
                                }
                            }
                        )

                        Button {
                            Log().info(tag: TAG, message: "Going to next page")
                            self.pageNumber = self.pageNumber + 1
                        } label: {
                            Image("next_page")
                        }
                        .disabled(pageNumber == (totalPages - 1))
                    }
                }
                .onTapGesture {
                    Log().debug(tag: TAG, message: "Comic page tapped")
                }
                .navigationTitle(title)
                .toolbar {
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

#Preview {
    ReadingPageView(
        comicFilename: COMIC_BOOK_LIST[0].filename,
        pageFilename: (COMIC_BOOK_LIST[0].pages[0] as! ComicPage).filename,
        title: (COMIC_BOOK_LIST[0].pages[0] as! ComicPage).filename,
        pageNumber: .constant(5.0),
        totalPages: 10,
        onStopReading: {}
    )
}
