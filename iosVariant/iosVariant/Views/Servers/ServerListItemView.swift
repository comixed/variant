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

private let TAG = "ServerListItemView"

struct ServerListItemView: View {
    let server: Server

    var onServerClicked: (Server) -> Void

    var body: some View {
        VStack(alignment: .leading) {
            Text(server.name)
                .font(.headline)
            Text(server.url)
                .font(.subheadline)
            Text(server.username)
                .font(.body)
        }
        .onTapGesture {
            Log().debug(tag: TAG, message: "Server list item tapped")
            onServerClicked(self.server)
        }
    }
}

#Preview {
    ServerListItemView(
        server: SERVER_LIST[0],
        onServerClicked: { _ in }
    )
}
