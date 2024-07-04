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
struct ServerManagementView: View {
  @State private var editMode = false
  @State private var server: Server? = nil

  var servers: [Server]
  var directory: String
  var links: [AcquisitionLink]

  var onBrowseServer: (Server) -> Void
  var onSaveServer: (Server) -> Void
  var onDeleteServer: (Server) -> Void

  var body: some View {
    NavigationStack {
      if editMode {
        if let item = server {
          ServerEditView(
            server: item,
            onSave: { server in
              onSaveServer(server)
              editMode = false
            },
            onCancel: { editMode = false }
          )
        } else {
          Text("No server")
        }
      } else {
        List(servers, id: \.id) { item in
          NavigationLink(destination: {
            if editMode {
              ServerEditView(
                server: item,
                onSave: { server in
                  onSaveServer(server)
                  editMode = false
                },
                onCancel: { editMode = false }
              )
            } else {
              ServerBrowseView(
                server: item,
                directory: directory,
                links: links,
                onLoadDirectory: { server, directory in },
                onClose: {}
              )
            }
          }) {
            Text(item.name)
              .swipeActions(allowsFullSwipe: false) {
                Button {
                  server = item
                  editMode = true
                } label: {
                  Label("Edit", systemImage: "pencil")
                }
                .tint(.green)

                Button(role: .destructive) {
                  onDeleteServer(item)
                } label: {
                  Label("Delete", systemImage: "trash.fill")
                }
              }
          }
        }
        .toolbar {
          Button(action: {
            server = Server(id: nil, name: "", url: "", username: "", password: "")
            editMode = true
          }) {
            Image(systemName: "plus")
          }
          .accessibilityLabel("New Server")
        }
      }
    }
  }
}

@available(iOS 17.0, *)#Preview{
  ServerManagementView(
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
    onBrowseServer: { _ in },
    onSaveServer: { _ in },
    onDeleteServer: { _ in })
}
