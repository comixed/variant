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

struct HomeView: View {
  @State private var currentScreen: NavigationTarget? = NavigationTarget.Comics
  @State var servers: [Server] = SERVER_LIST
  @State var currentServer: Server?
  @State var editServer: Bool = false

  var body: some View {
    NavigationSplitView {
      List(NavigationTarget.allCases, id: \.self, selection: $currentScreen) { target in
        Text(target.rawValue)
      }
    } detail: {
      switch self.currentScreen {
      case .Comics: Text("Comics detail!")
      case .Servers:
        if self.currentServer != nil {
          if self.editServer {
            ServerEditView(
              server: self.currentServer!,
              onSaveChanges: { _ in
                self.editServer = false
                self.currentServer = nil
              },
              onCancelChanges: {
                self.editServer = false
                self.currentServer = nil
              })
          } else {
            ServerDetailView(
              server: self.currentServer!)
          }
        } else {
          ServerListView(
            servers: self.servers,
            onEditServer: { server in
              self.currentServer = server
              self.editServer = true
            },
            onDeleteServer: { _ in },
            onBrowseServer: { _ in })
        }
      case .Settings: Text("Settings detail!")
      default: Text("No idea what \(String(describing: self.currentScreen)) is")
      }
    }
  }
}

#Preview {
  HomeView()
}
