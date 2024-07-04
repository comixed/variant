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

struct ServerEditView: View {
  var server: Server

  var onSave: (Server) -> Void
  var onCancel: () -> Void

  @State private var name: String
  @State private var url: String
  @State private var username: String
  @State private var password: String

  init(server: Server, onSave: @escaping (Server) -> Void, onCancel: @escaping () -> Void) {
    self.server = server
    self.onSave = onSave
    self.onCancel = onCancel
    self.name = server.name
    self.url = server.url
    self.username = server.username
    self.password = server.password
  }

  var body: some View {
    NavigationStack {
      Form {
        Section(header: Text("Server Details")) {
          TextField("The server's name", text: $name)
            .textInputAutocapitalization(.words)
          TextField("The server's URL", text: $url)
            .keyboardType(.URL)
            .textContentType(.URL)
            .textInputAutocapitalization(.never)
        }
        Section(header: Text("Account Details")) {
          TextField("The user's name", text: $username)
            .keyboardType(.emailAddress)
            .textInputAutocapitalization(.never)
          SecureField("The user's password", text: $password)
            .textInputAutocapitalization(.never)
        }
      }
      .padding(.horizontal, 16)
    }
    .toolbar {
      ToolbarItem(placement: .topBarLeading) {
        Button("Save") {
          onSave(
            Server(
              id: server.id,
              name: $name.wrappedValue,
              url: $url.wrappedValue,
              username: $username.wrappedValue,
              password: $password.wrappedValue
            ))
        }
      }
      ToolbarItem(placement: .topBarTrailing) {
        Button("Cancel") {
          onCancel()
        }
      }
    }
  }
}

#Preview {
  ServerEditView(
    server: Server(
      id: "1",
      name: "Server 1",
      url: "http://www.comixedproject.org:7171/opds",
      username: "reader@comixedproject.org",
      password: "my!password"
    ),
    onSave: { _ in },
    onCancel: {})
}
