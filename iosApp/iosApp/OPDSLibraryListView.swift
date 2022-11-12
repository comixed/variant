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

struct OPDSLibraryListView: View {
    let libraries: [OPDSLibrary]

	var body: some View {
        Text("Library List")
		List {
            ForEach(libraries, id: \.self) { library in
            OPDSLibraryEntryView(library: library)
            }
		}
	}
}

struct OPDSLibraryListView_Previews: PreviewProvider {
	static var previews: some View {
        OPDSLibraryListView(libraries: [
            OPDSLibrary(name: "Library 1", url: "http://localhost:7171/library1", username: "admin1", password: "password1"),
            OPDSLibrary(name: "Library 2", url: "http://localhost:7171/library2", username: "admin2", password: "password2"),
            OPDSLibrary(name: "Library 3", url: "http://localhost:7171/library3", username: "admin3", password: "password3"),
            OPDSLibrary(name: "Library 4", url: "http://localhost:7171/library4", username: "admin4", password: "password4"),
            OPDSLibrary(name: "Library 5", url: "http://localhost:7171/library5", username: "admin5", password: "password5"),
        ])
	}
}
