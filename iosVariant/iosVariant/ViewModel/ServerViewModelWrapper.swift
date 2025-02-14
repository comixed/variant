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

import Observation
import Variant

@available(iOS 17.0, *)
@Observable
final class ServerViewModelWrapper {
  let viewModel: ServerViewModel = Koin.instance.get()

  private(set) var serverList: [Server] = []

  init() {
    viewModel.onServerListUpdated = { [weak self] servers in
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
