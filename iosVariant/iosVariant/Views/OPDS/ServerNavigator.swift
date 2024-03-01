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

struct ServerNavigator: View {
  @State var server: Server?
  var serverList: [Server]

  var body: some View {
    NavigationSplitView {
      ServerList(
        serverList: [
          Server(
            id: "1", name: "Home Server 1", url: "http://comixedproject.org:7171/opds",
            username: "admin@comixedproject.org", password: "my!password"),
          Server(
            id: "2", name: "Home Server 2", url: "http://comixedproject.org:7171/opds",
            username: "admin@comixedproject.org", password: "my!password"),
          Server(
            id: "3",
            name: "Home Server 3",
            url: "http://comixedproject.org:7171/opds",
            username: "admin@comixedproject.org",
            password: "my!password"
          ),
          Server(
            id: "4",
            name: "Home Server 4",
            url: "http://comixedproject.org:7171/opds",
            username: "admin@comixedproject.org",
            password: "my!password"
          ),
          Server(
            id: "5",
            name: "Home Server 5",
            url: "http://comixedproject.org:7171/opds",
            username: "admin@comixedproject.org",
            password: "my!password"
          ),
        ],
        onSelect: onSelectServer,
        onDelete: onDeleteServer
      )
    } detail: {
      if $server.wrappedValue != nil {
        ServerDetail(server: $server.wrappedValue!)
      }
    }
  }
}

extension ServerNavigator {
  fileprivate func onSelectServer(selectedServer: Server) {
    server = selectedServer
  }

  fileprivate func onDeleteServer(server: Server) {

  }
}

#Preview {
  ServerNavigator(serverList: [
    Server(
      id: "1", name: "Home Server 1", url: "http://comixedproject.org:7171/opds",
      username: "admin@comixedproject.org", password: "my!password"),
    Server(
      id: "2", name: "Home Server 2", url: "http://comixedproject.org:7171/opds",
      username: "admin@comixedproject.org", password: "my!password"),
    Server(
      id: "3",
      name: "Home Server 3",
      url: "http://comixedproject.org:7171/opds",
      username: "admin@comixedproject.org",
      password: "my!password"
    ),
    Server(
      id: "4",
      name: "Home Server 4",
      url: "http://comixedproject.org:7171/opds",
      username: "admin@comixedproject.org",
      password: "my!password"
    ),
    Server(
      id: "5",
      name: "Home Server 5",
      url: "http://comixedproject.org:7171/opds",
      username: "admin@comixedproject.org",
      password: "my!password"
    ),
  ])
}
