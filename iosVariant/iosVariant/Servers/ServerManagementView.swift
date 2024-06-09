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
    @State var servers: [Server]
    @State private var selectedServer: Server? = nil

    var onSaveServer: (Server) -> ()
    var onBrowseServer: (Server) -> ()
    var onDeleteServer: (Server) -> ()

    var body: some View {
        ZStack {
            if let server = $selectedServer.wrappedValue {
                Text(server.name)
            }
            VStack {
                NavigationSplitView {
                    List(servers, id: \.id, selection: $selectedServer) { server in
                        NavigationLink(value: server) {
                            HStack {
                                Image(systemName: "server.rack")
                                Text(server.name)
                                Spacer()
                            }
                        }
                        .navigationTitle("Servers")
                    }
                } detail: {
                    if let server = $selectedServer.wrappedValue {
                        ServerDetail(
                            server: server,
                            onEdit: { selectedServer = server },
                            onBrowse: { onBrowseServer(server) },
                            onDelete: {
                                onDeleteServer(server)
                            })
                    }
                }
                Spacer()
                HStack {
                    Spacer()
                    Button(action: {}, label: {
                        Text("+")
                            .font(.system(.largeTitle))
                            .frame(width: 77, height: 70)
                            .foregroundColor(Color.white)
                            .padding(.bottom, 7)
                    })
                    .background(Color.blue)
                    .cornerRadius(38.5)
                    .padding()
                    .shadow(color: Color.black.opacity(0.3),
                            radius: 3,
                            x: 3,
                            y: 3)
                }
            }
        }
    }
}

@available(iOS 17.0, *)
#Preview {
    ServerManagementView(servers: [
        Server(id: "1", name: "My Server", url: "http://www.comixedproject.org:7171/opds", username: "reader@comixedproject.org", password: "my!password"),
    ],
    onSaveServer: { _ in },
    onBrowseServer: { _ in },
    onDeleteServer: { _ in })
}
