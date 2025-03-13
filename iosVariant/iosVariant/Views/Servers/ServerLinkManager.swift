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

import Foundation
import Variant

@MainActor
class ServerLinkManager: ObservableObject {
  let viewModel: ServerLinkViewModel = Koin.instance.get()

  @Published var serverLinkList: [ServerLink] = []

  init() {
    self.viewModel.onServerLinkListUpdated = { [weak self] serverLinks in
      self?.serverLinkList = serverLinks
    }
    self.serverLinkList = self.viewModel.serverLinkList.value as! [ServerLink]
  }

  func hasLinks(server: Server, directory: String) -> Bool {
    return self.viewModel.hasLinks(server: server, directory: directory)
  }

  func downloadLinks(server: Server, directory: String, reload: Bool) {
    if reload || !self.hasLinks(server: server, directory: directory) {
      Task {
        await loadServerLinks(
          server: server, directory: directory,
          onSuccess: { links in
            self.saveLinks(
              server: server, directory: directory, links: links)
          },
          onFailure: {})
      }
    } else {
      self.loadLinks(server: server, directory: directory)
    }
  }

  func saveLinks(server: Server, directory: String, links: [ServerLink]) {
    self.viewModel.saveLinks(server: server, directory: directory, links: links)
  }

  func loadLinks(server: Server, directory: String) {
    self.viewModel.loadLinks(server: server, directory: directory)
  }
}

extension ServerLink: Identifiable {
  public var id: Int {
    if let serverLinkId = self.serverLinkId {
      return serverLinkId.intValue
    } else {
      return -1
    }
  }
}

@MainActor func previewableServerLinkManager() -> ServerLinkManager {
  let result = ServerLinkManager()
  result.serverLinkList = SERVER_LINK_LIST
  return result
}
