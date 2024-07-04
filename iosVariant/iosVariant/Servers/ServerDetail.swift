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

struct ServerDetail: View {
  var server: Server
  var onEdit: () -> Void
  var onBrowse: () -> Void
  var onDelete: () -> Void

  var body: some View {
    NavigationStack {
      VStack {
        Text(server.name).font(.title).bold()
        Text(server.url).font(.subheadline).foregroundStyle(Color.secondary)
        Text(server.username)
        Spacer()
        Text("Bottom")
      }
    }
  }
}

#Preview {
  ServerDetail(
    server: Server(
      id: "1", name: "Server 1", url: "http://www.comixedproject.org:7171/opds",
      username: "reader@comixedproject.org", password: "my!password"),
    onEdit: {},
    onBrowse: {},
    onDelete: {})
}
