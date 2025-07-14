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

private let TAG = "ComicBookListView"

struct ComicBookListView: View {
    let comicBookList: [ComicBook]
    let selectionList: [String]

    let columns = [GridItem(.adaptive(minimum: 128))]

    var onClick: (ComicBook) -> Void

    var body: some View {
        NavigationStack {
            ScrollView {
                LazyVGrid(columns: columns, spacing: 20) {
                    ForEach(comicBookList, id: \.path) { comicBook in
                        ComicBookListItemView(
                            comicBook: comicBook,
                            selected: selectionList.contains(comicBook.path),
                            onClick: { comicBook in
                                onClick(comicBook)
                            }
                        )
                    }
                }
                .padding(.horizontal)
            }
            .navigationTitle(
                String(
                    localized: "comic-book-list.title",
                    defaultValue: "Comic List"
                )
            )
        }
    }
}

#Preview {
    ComicBookListView(
        comicBookList: COMIC_BOOK_LIST,
        selectionList: [COMIC_BOOK_LIST[0].path],
        onClick: { _ in }
    )
}
