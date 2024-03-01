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

  var body: some View {
    VStack {
      Text("\(server.name)")
        .font(.headline)

      Text("server-url-description \(server.url)")
        .font(.subheadline)
        .fontWeight(.bold)

      Text("server-username-description \(server.username)")
        .font(.none)

      Text("server-password-description \(server.password)")
        .font(.none)

      Spacer()
    }
  }
}

#Preview {
  ServerDetail(
    server: Server(
      id: "1", name: "Home Server 1", url: "http://comixedproject.org:7171/opds",
      username: "admin@comixedproject.org", password: "my!password"))
}
