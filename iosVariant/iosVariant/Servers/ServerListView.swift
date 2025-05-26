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

struct ServerListView: View {
    let serverList: [Server]

    @State var selected: Server?

    var onEditServer: (Server) -> Void

    var body: some View {
        ZStack {
            List(serverList, id: \.serverId, selection: $selected) { server in
                ServerListItemView(server: server)
                    .swipeActions(edge: .leading, allowsFullSwipe: false) {
                        Button {

                        } label: {
                            Label("Delete", systemImage: "trash.fill")
                        }
                        .tint(.red)
                    }
                    .swipeActions(edge: .trailing) {
                        Button {
                            onEditServer(server)
                        } label: {
                            Label("Edit", systemImage: "pencil")
                        }
                        .tint(.green)
                    }
            }

            VStack {
                Spacer()

                HStack {
                    Spacer()

                    Button(
                        action: {
                            onEditServer(
                                Server(
                                    serverId: nil,
                                    name: "",
                                    url: "",
                                    username: "",
                                    password: ""
                                )
                            )
                        },
                        label: {
                            Text("+").font(.system(.largeTitle))
                                .frame(width: 77, height: 70)
                                .foregroundColor(Color.white)
                                .padding(.bottom, 7)
                        }
                    ).background(Color.blue)
                        .cornerRadius(38.5)
                        .padding()
                        .shadow(
                            color: Color.black.opacity(0.3),
                            radius: 3,
                            x: 3,
                            y: 3
                        )
                }
            }
        }
    }
}

#Preview {
    ServerListView(serverList: SERVER_LIST, onEditServer: { _ in })
}
