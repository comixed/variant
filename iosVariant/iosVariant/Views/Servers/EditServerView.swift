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

private let TAG = "EditServerView"

struct EditServerView: View {
    let server: Server

    var onSaveChanges: (Server) -> Void
    var onCancelChanges: () -> Void

    @State private var name: String = ""
    @State private var url: String = ""
    @State private var username: String = ""
    @State private var password: String = ""

    init(
        server: Server,
        onSaveChanges: @escaping (Server) -> Void,
        onCancelChanges: @escaping () -> Void
    ) {
        self.server = server

        self.name = server.name
        self.url = server.url
        self.username = server.username
        self.password = server.password

        self.onSaveChanges = onSaveChanges
        self.onCancelChanges = onCancelChanges
    }

    var body: some View {
        NavigationView {
            VStack {
                Text("\(server.name)").font(.title)

                Section(header: Text("Server Details").font(.headline)) {
                    TextField("Server name", text: $name)
                    TextField("Server address", text: $url)
                        .keyboardType(.URL)
                        .textContentType(.URL)
                        .disableAutocorrection(true)
                        .autocapitalization(.none)
                }

                Section(header: Text("Account Details").font(.headline)) {
                    TextField("Username", text: $username)
                        .textContentType(.emailAddress)
                        .disableAutocorrection(true)
                        .autocapitalization(.none)
                    SecureField("Password", text: $password)
                        .textContentType(.password)
                        .disableAutocorrection(true)
                        .autocapitalization(.none)
                }

                Spacer()
            }
            .padding()
            .toolbar {
                let saveCaption = self.server.serverId != nil ? "Save" : "Add"

                Button(saveCaption) {
                    self.server.name = self.name
                    self.server.url = self.url
                    self.server.username = username
                    self.server.password = password
                    Log().debug(
                        tag: TAG,
                        message:
                            "id=\(String(describing: self.server.serverId))"
                    )
                    onSaveChanges(server)
                }
                Button("Cancel") {
                    onCancelChanges()
                }
            }
        }.navigationViewStyle(StackNavigationViewStyle())
    }
}

#Preview {
    EditServerView(
        server: SERVER_LIST[0],
        onSaveChanges: { _ in },
        onCancelChanges: {}
    )
}
