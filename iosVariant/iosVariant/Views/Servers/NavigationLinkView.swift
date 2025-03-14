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

struct NavigationLinkView: View {
  let server: Server
  let serverLink: ServerLink

  var onBrowseServer: (Server, String, Bool) -> Void

  var body: some View {
    HStack {
      VStack(alignment: .leading) {
        Text("\(serverLink.title)").font(.headline)
      }

      Spacer()

      Button(action: {
        onBrowseServer(server, serverLink.downloadLink, false)
      }) {
        Image(systemName: "chevron.right")
      }
    }
  }
}

#Preview {
  NavigationLinkView(
    server: SERVER_LIST[0], serverLink: SERVER_LINK_LIST[0],
    onBrowseServer: { _, _, _ in })
}
