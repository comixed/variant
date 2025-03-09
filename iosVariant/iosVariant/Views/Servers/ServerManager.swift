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

class ServerManager: ObservableObject {
  let viewModel: ServerViewModel = Koin.instance.get()

  @Published var serverList: [Server] = []

  init() {
    self.viewModel.onServerListUpdated = { [weak self] servers in
      self?.serverList = servers
    }
    self.serverList = self.viewModel.serverList.value as! [Server]
  }

  func saveServer(server: Server) {
    self.viewModel.saveServer(server: server)
  }

  func deleteServer(server: Server) {
    self.viewModel.deleteServer(server: server)
  }
}

extension Server: Identifiable {
  public var id: Int {
    if let serverId = self.serverId {
      return serverId.intValue
    } else {
      return -1
    }
  }
}

func previewableServerManager() -> ServerManager {
  let result = ServerManager()
  result.serverList = SERVER_LIST
  return result
}
