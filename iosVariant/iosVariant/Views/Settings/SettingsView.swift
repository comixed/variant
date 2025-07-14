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

private let TAG = "SettingsView"

struct SettingsView: View {
    let address: String
    let username: String
    let password: String

    let onSaveChanges: (String, String, String) -> Void

    var body: some View {
        VStack {
            EditServerView(
                address: address,
                username: username,
                password: password,
                onSaveChanges: onSaveChanges
            )
        }
    }
}

#Preview {
    SettingsView(
        address: "http://www.comixedproject.org:7171",
        username: "reader@comixedproject.org",
        password: "the!password",
        onSaveChanges: { _, _, _ in }
    )
}
