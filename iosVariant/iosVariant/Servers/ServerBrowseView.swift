/*
 * Variant - A digital comic book reading application for the iPad and Android tablets.
 * Copyright (C) 2024, The ComiXed Project
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
import Variant

struct ServerBrowseView: View {
  var server: Server
  var directory: String
  var links: [AcquisitionLink]
  var onLoadDirectory: (Server, String) -> Void
  var onClose: () -> Void

  var body: some View {
    Text("\(server.name)")
    ScrollView {
      LazyVStack(alignment: .leading) {
        List(links, id: \.id) { link in
          Text(link.title)
        }
      }
    }
  }
}

#Preview {
  ServerBrowseView(
    server: Server(
      id: "1",
      name: "Server 1",
      url: "http://www.comixedproject.org:7171/opds",
      username: "reader@comixedproject.org",
      password: "my!password"),
    directory: "",
    links: [],
    onLoadDirectory: { _, _ in },
    onClose: {}
  )
}
