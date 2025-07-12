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

private let TAG = "ComicBooksView"

struct ComicBooksView: View {
    let comicBookList: [ComicBook]
    let selectionMode: Bool
    let selectionList: [String]

    var onSetSelectionMode: (Bool) -> Void
    var onComicClicked: (ComicBook) -> Void
    var onDeleteComics: () -> Void

    var body: some View {
        NavigationStack {
            ComicBookListView(
                comicBookList: comicBookList,
                selectionList: selectionList,
                onClick: { comicBook in onComicClicked(comicBook) }
            )
            .toolbar {
                ToolbarItemGroup(placement: .bottomBar) {
                    if selectionMode {
                        Button {
                            onSetSelectionMode(false)
                        } label: {
                            Image("selection_mode_on")
                        }

                        Button {
                            onDeleteComics()
                        } label: {
                            Image(systemName: "trash.fill")
                        }
                        .disabled(selectionList.isEmpty)
                    } else {
                        Button {
                            onSetSelectionMode(true)
                        } label: {
                            Image("selection_mode_off")
                        }
                    }

                    Spacer()
                }
            }
        }
    }
}

#Preview {
    ComicBooksView(
        comicBookList: COMIC_BOOK_LIST,
        selectionMode: false,
        selectionList: [],
        onSetSelectionMode: { _ in },
        onComicClicked: { _ in },
        onDeleteComics: { }
    )
}

#Preview("selection mode on") {
    ComicBooksView(
        comicBookList: COMIC_BOOK_LIST,
        selectionMode: true,
        selectionList: [COMIC_BOOK_LIST[0].path],
        onSetSelectionMode: { _ in },
        onComicClicked: { _ in },
        onDeleteComics: { }
    )
}

#Preview("selection mode on no selections") {
    ComicBooksView(
        comicBookList: COMIC_BOOK_LIST,
        selectionMode: true,
        selectionList: [],
        onSetSelectionMode: { _ in },
        onComicClicked: { _ in },
        onDeleteComics: { }
    )
}
