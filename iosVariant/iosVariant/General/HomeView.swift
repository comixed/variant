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

@available(iOS 17.0, *)
struct HomeView: View {
  var servers: [Server]
  var directory: String
  var links: [AcquisitionLink]

  var onSaveServer: (Server) -> Void
  var onDeleteServer: (Server) -> Void

  var body: some View {
    TabView {
      ServerManagementView(
        servers: servers,
        directory: directory,
        links: links,
        onBrowseServer: { _ in },
        onSaveServer: { server in onSaveServer(server) },
        onDeleteServer: { server in onDeleteServer(server) }
      )
      .tabItem {
        Label("Servers", systemImage: "person")
      }

      Text("Comics")
        .tabItem {
          Label("Comics", systemImage: "list.bullet")
        }

      Text("Settings")
        .tabItem {
          Label("Settings", systemImage: "gear")
        }
    }
  }
}

@available(iOS 17.0, *)#Preview{
  HomeView(
    servers: [
      Server(
        id: "1",
        name: "My Server",
        url: "http://www.comixedproject.org:7171/opds",
        username: "reader@comixedproject.org",
        password: "my!password"
      )
    ],
    directory: "",
    links: [],
    onSaveServer: { _ in },
    onDeleteServer: { _ in }
  )
}
