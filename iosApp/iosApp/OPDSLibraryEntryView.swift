/*
 * Prestige - A digital comic book reader.
 * Copyright (C) 2022, The ComiXed Project
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

struct OPDSLibraryEntryView: View {
    let library: shared.OPDSLibrary
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(library.name)
                .font(.headline)            
            Text(library.url)
            Text("\(library.username)/\(library.password)")
        }
    }
}

struct OPDSLibraryEntryView_Previews: PreviewProvider {
    static var previews: some View {
        OPDSLibraryEntryView(
            library: OPDSLibrary(
                name: "Library 1",
                url: "http://www.comixedproject.org:7171/comics/lib1",
                username: "admin1",
                password: "password1")
        )
    }
}
