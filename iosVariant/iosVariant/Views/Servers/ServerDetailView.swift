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
import Variant

struct ServerDetailView: View {
  let server: Server
  let selected: Bool
  var onServerSelected: (Server?) -> Void

  var body: some View {
    let background = selected ? Color.gray.opacity(0.1) : Color.clear

    VStack(alignment: .leading) {
      Text(server.name)
        .font(.headline)
      Text(server.url)
        .font(.subheadline)
      Text(server.username)
        .font(.body)
    }.background(background)
      .onTapGesture {
        if selected {
          onServerSelected(nil)
        } else {
          onServerSelected(server)
        }
      }
  }
}

#Preview("selected") {
  ServerDetailView(
    server: Server(
      serverId: 1, name: "Test Server", url: "http://www.comixedproject.org:7171/opds/root.xml",
      username: "reader@comixedproject.org", password: "the!password"),
    selected: true, onServerSelected: { _ in })
}

#Preview("not selected") {
  ServerDetailView(
    server: Server(
      serverId: 1, name: "Test Server", url: "http://www.comixedproject.org:7171/opds/root.xml",
      username: "reader@comixedproject.org", password: "the!password"), selected: false,
    onServerSelected: { _ in })
}
