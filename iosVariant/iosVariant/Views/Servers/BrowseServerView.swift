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

private let TAG = "BrowseServerView"

struct BrowseServerView: View {
  let server: Server
  let serverLinkList: [ServerLink]

  @State var directory: String = ""

  var onFollowLink: (Server, String, Bool) -> Void
  var onStopBrowsing: () -> Void

  var body: some View {
    ZStack {
      List(serverLinkList, id: \.self) { serverLink in
        if serverLink.linkType == ServerLinkType.navigation {
          NavigationLinkView(
            server: server, serverLink: serverLink,
            onBrowseServer: { server, directory, reload in
              onFollowLink(server, directory, reload)
            })
        } else {
          PublicationLinkView(
            server: server, serverLink: serverLink,
            onBrowseServer: { _, _, _ in })
        }
      }
      VStack(alignment: .leading) {
        Text("\(server.name)").font(.title)
        Spacer()
        Text("\(directory)")
      }
    }
  }
}

#Preview("root") {
  BrowseServerView(
    server: SERVER_LIST[0],
    serverLinkList: SERVER_LINK_LIST,
    onFollowLink: { _, _, _ in },
    onStopBrowsing: {}
  )
}

#Preview("root") {
  BrowseServerView(
    server: SERVER_LIST[0],
    serverLinkList: SERVER_LINK_LIST,
    onFollowLink: { _, _, _ in },
    onStopBrowsing: {}
  )
}
