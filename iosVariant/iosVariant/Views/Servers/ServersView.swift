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

struct ServersView: View {
  @EnvironmentObject var serverManager: ServerManager

  @State var selected: Server?
  @State var isEditing = false
  @State var isDeleting = false
  @State var isBrowsing = false

  var body: some View {
    List(serverManager.serverList, id: \.serverId, selection: $selected) { server in
      ServerDetailView(
        server: server,
        onTapped: { self.isBrowsing = true }
      ).swipeActions(edge: .leading, allowsFullSwipe: false) {
        Button {
          serverManager.deleteServer(server: server)
        } label: {
          Label("Delete", systemImage: "trash.fill")
        }
        .tint(.red)
      }
      .swipeActions(edge: .trailing) {
        Button {
          self.isEditing = true
        } label: {
          Label("Edit", systemImage: "pencil")
        }
        .tint(.green)
      }
    }
    .listStyle(.plain)
    .navigationTitle("Server List")
    .toolbar {
      Button {
        self.selected = nil
        self.isEditing = true
      } label: {
          Label("Add", systemImage: "plus")
      }
    }
    .sheet(isPresented: self.$isEditing) {
      ServerEditView(
        server: self.selected
          ?? Server(serverId: nil, name: "", url: "", username: "", password: ""),
        onSaveChanges: { server in
          self.serverManager.saveServer(server: server)
          self.isEditing = false
        },
        onCancelChanges: { self.isEditing = false })
    }
  }
}

struct ServersView_Previews: PreviewProvider {
  static var serverManager = previewableServerManager()

  static var previews: some View {
    ServersView()
      .environmentObject(serverManager)
  }
}
