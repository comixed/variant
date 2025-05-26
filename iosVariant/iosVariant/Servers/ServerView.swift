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

private let TAG = "ServerView"

struct ServerView: View {
    @StateViewModel var serverViewModel: ServerViewModel = Koin.instance.get()

    var body: some View {
        VStack {
            if self.serverViewModel.editingServer != nil {
                EditServerView(
                    server: self.serverViewModel.editingServer!,
                    onSaveChanges: { server in
                        self.serverViewModel.saveServer(
                            server: server
                        )
                        self.serverViewModel.editServer(server: nil)
                    },
                    onCancelChanges: {
                        self.serverViewModel.editServer(server: nil)
                    }
                )
            } else {
                ServerListView(
                    serverList: self.serverViewModel.serverList,
                    onEditServer: { server in
                        Log().debug(tag: TAG, message: "Editing server: \(server.name)")
                        self.serverViewModel.editServer(server: server)}
                )
            }
        }
    }
}

#Preview {
    ServerView()
}
