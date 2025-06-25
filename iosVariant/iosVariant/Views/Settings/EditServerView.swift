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

import KMPObservableViewModelSwiftUI
import SwiftUI
import Variant

private let TAG = "EditServerView"

struct EditServerView: View {
    let address: String
    let username: String
    let password: String

    var onSaveChanges: (String, String, String) -> Void
    var onCancelChanges: () -> Void

    @State private var addressValue: String = ""
    @State private var usernameValue: String = ""
    @State private var passwordValue: String = ""

    init(
        address: String,
        username: String,
        password: String,
        onSaveChanges: @escaping (String, String, String) -> Void,
        onCancelChanges: @escaping () -> Void
    ) {
        self.address = address
        self.username = username
        self.password = password

        self.addressValue = address
        self.usernameValue = username
        self.passwordValue = password

        self.onSaveChanges = onSaveChanges
        self.onCancelChanges = onCancelChanges
    }

    var body: some View {
        NavigationView {
            VStack {
                Section(header: Text("Server Details").font(.headline)) {
                    TextField("Server address", text: $addressValue)
                        .keyboardType(.URL)
                        .textContentType(.URL)
                        .disableAutocorrection(true)
                        .autocapitalization(.none)
                }

                Section(header: Text("Account Details").font(.headline)) {
                    TextField("Username", text: $usernameValue)
                        .textContentType(.emailAddress)
                        .disableAutocorrection(true)
                        .autocapitalization(.none)
                    SecureField("Password", text: $passwordValue)
                        .textContentType(.password)
                        .disableAutocorrection(true)
                        .autocapitalization(.none)
                }

                Spacer()
            }
            .padding()
            .toolbar {
                Button("Save") {
                    onSaveChanges(addressValue, usernameValue, passwordValue)
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
        address: "http://myserver.comixedproject.org:7171",
        username: "reader@comixedproject.org",
        password: "my!password",
        onSaveChanges: { _, _, _ in },
        onCancelChanges: {}
    )
}
