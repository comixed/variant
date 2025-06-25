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

private let TAG = "ComicBookListItemView"
private let BYTES_PER_MB = 1024.0 * 1024.0

struct ComicBookListItemView: View {
    let comicBook: ComicBook

    var onComicBookClicked: (ComicBook) -> Void

    var filesize: String {
        return
            "\(String(format: "%.1f", Double(comicBook.size) / BYTES_PER_MB)) mb"
    }

    var lastModified: String {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "MM/dd/YYYY HH:mm a"
        return
            "\(dateFormatter.string(from: Date(timeIntervalSince1970: TimeInterval(comicBook.lastModified))))"
    }

    var body: some View {
        VStack(alignment: .leading) {
            Text(comicBook.filename)
                .font(.headline)
            Text(filesize)
                .font(.subheadline)
            Text(lastModified)
                .font(.subheadline)
        }
        .onTapGesture {
            Log().debug(tag: TAG, message: "Comic book item tapped")
            onComicBookClicked(self.comicBook)
        }
    }
}

#Preview {
    ComicBookListItemView(
        comicBook: COMIC_BOOK_LIST[0],
        onComicBookClicked: { _ in }
    )
}
