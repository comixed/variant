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
  @EnvironmentObject var serverLinkManager: ServerLinkManager

  @State var selected: Server?
  @State var isEditing = false
  @State var isDeleting = false
  @State var isBrowsing = false

  @State var server: Server? = nil
  @State var currentDirectory = ""

  var body: some View {
    if self.isBrowsing {
      var serverId = self.selected!.serverId?.int64Value
      var parentLink = self.serverLinkManager.serverLinkList
        .first(where: { $0.serverId == serverId && $0.downloadLink == self.currentDirectory })
      var currentLink = self.serverLinkManager.serverLinkList
        .first(where: { $0.serverId == serverId && $0.directory == self.currentDirectory })
      var serverLinkList = self.serverLinkManager.serverLinkList
        .filter { $0.serverId == serverId }
        .filter {
          $0.directory == currentDirectory
        }
      var title = "\(self.selected!.name)"
      /*
        if let link = parentLink {
            title = "\(link.title)"
        }
         */
      BrowseServerView(
        server: self.selected!,
        title: title,
        parentLink: parentLink,
        serverLinkList: serverLinkList,
        onFollowLink: { server, directory, reload in
          self.serverLinkManager.downloadLinks(server: server, directory: directory, reload: reload)
          self.currentDirectory = directory
        },
        onStopBrowsing: { self.isBrowsing = false })
    } else {
      List(serverManager.serverList, id: \.serverId, selection: $selected) { server in
        ServerDetailView(
          server: server,
          onTapped: {
            self.isBrowsing = true
            self.selected = server
            self.currentDirectory = server.url
            self.serverLinkManager.downloadLinks(
              server: server, directory: server.url, reload: false)
          }
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
      .toolbar(content: {
        Button {
          self.selected = nil
          self.isEditing = true
        } label: {
          Label("Add", systemImage: "plus")
        }
      })
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
}

struct ServersView_Previews: PreviewProvider {
  static var previews: some View {
    ServersView()
      .environmentObject(previewableServerManager())
      .environmentObject(previewableServerLinkManager())
  }
}
