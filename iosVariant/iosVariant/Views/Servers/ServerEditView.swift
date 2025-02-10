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

struct ServerEditView: View {
  private let TAG = "ServerEditView"

  @State var server: Server

  var onSaveChanges: (Server) -> Void
  var onCancelChanges: () -> Void

  var body: some View {
    VStack {
      Text("\(server.name)").font(.headline)

      Section {
        TextField("Server name", text: $server.name)
        TextField("Server address", text: $server.url)
      }

      Section {
        TextField("Username", text: $server.username)
        TextField("Password", text: $server.password)
      }

      Spacer()
    }
    .padding()
    .toolbar {
      let saveCaption = self.server.serverId != nil ? "Save" : "Add"

      Button(saveCaption) {
        onSaveChanges($server.wrappedValue)
      }
      Button("Close") {
        onCancelChanges()
      }
    }
  }
}

#Preview("Update") {
  ServerEditView(server: SERVER_LIST[0], onSaveChanges: { _ in }, onCancelChanges: {})
}

#Preview("Save") {
  ServerEditView(
    server: Server(serverId: nil, name: "", url: "", username: "", password: ""),
    onSaveChanges: { _ in }, onCancelChanges: {})
}
