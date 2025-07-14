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
import shared

private let TAG = "EditServerView"

struct EditServerView: View {
    let address: String
    let username: String
    let password: String

    var onSaveChanges: (String, String, String) -> Void

    @State private var addressValue: String = ""
    @State private var usernameValue: String = ""
    @State private var passwordValue: String = ""

    init(
        address: String,
        username: String,
        password: String,
        onSaveChanges: @escaping (String, String, String) -> Void
    ) {
        self.address = address
        self.username = username
        self.password = password

        self.addressValue = address
        self.usernameValue = username
        self.passwordValue = password

        self.onSaveChanges = onSaveChanges
    }

    var body: some View {
        NavigationView {
            VStack {
                Section(header: Text("Server Details").font(.headline)) {
                    TextField(
                        String(
                            localized: "edit-server.label.server-address",
                            defaultValue: "Server address"
                        ),
                        text: $addressValue
                    )
                    .keyboardType(.URL)
                    .textContentType(.URL)
                    .disableAutocorrection(true)
                    .autocapitalization(.none)
                }

                Section(
                    header: Text(
                        String(
                            localized: "edit-server.header.account-details",
                            defaultValue: "Account Details"
                        )
                    ).font(.headline)
                ) {
                    TextField(
                        String(
                            localized: "edit-server.label.username",
                            defaultValue: "Username"
                        ),
                        text: $usernameValue
                    )
                    .textContentType(.emailAddress)
                    .disableAutocorrection(true)
                    .autocapitalization(.none)
                    SecureField(
                        String(
                            localized: "edit-server.label.password",
                            defaultValue: "Password"
                        ),
                        text: $passwordValue
                    )
                    .textContentType(.password)
                    .disableAutocorrection(true)
                    .autocapitalization(.none)
                }

                Spacer()
            }
            .padding()
            .toolbar {
                Button {
                    onSaveChanges(addressValue, usernameValue, passwordValue)
                } label: {
                    Image(systemName: "checkmark.circle.fill")
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
        onSaveChanges: { _, _, _ in }
    )
}
