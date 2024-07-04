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

struct ServerListView: View {
  var server: Server
  var onEdit: (Server) -> Void
  var onDelete: (Server) -> Void

  var body: some View {
    NavigationStack {
      VStack {
        Text("List View")
        Text(server.name).font(.title)
        Text(server.url).font(.body)
        Text(server.username).font(.body)
      }
    }
    .toolbar {
      ToolbarItem(placement: .topBarLeading) {
        Button("Edit") {
          onEdit(server)
        }
      }
      ToolbarItem(placement: .topBarTrailing) {
        Button("Delete") {
          onDelete(server)
        }
      }
    }
  }
}

#Preview {
  ServerListView(
    server: Server(
      id: "1",
      name: "My Server",
      url: "http://www.comixedproject.org:7171/opds",
      username: "reader@comixedproject.org",
      password: "my!password"
    ),
    onEdit: { _ in },
    onDelete: { _ in }
  )
}
