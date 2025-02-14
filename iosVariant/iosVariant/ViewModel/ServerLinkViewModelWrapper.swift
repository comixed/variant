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
final class ServerLinkViewModelWrapper {
  let viewModel: ServerLinkViewModel = Koin.instance.get()
  var server: Server?
  var directory: String = ""

  private(set) var serverLinkList: [ServerLink] = []

  init() {
    viewModel.onServerLinkListUpdated = { [weak self] links in
      self?.serverLinkList = links
    }
    self.serverLinkList = self.viewModel.serverLinkList.value as! [ServerLink]
  }

  func hasLinks(server: Server, directory: String) -> Bool {
    return self.viewModel.hasLinks(server: server, directory: directory)
  }

  func saveLinks(server: Server, directory: String, links: [ServerLink]) {
    self.viewModel.saveLinks(server: server, directory: directory, links: links)
  }

  func loadLinks(server: Server, directory: String) {
    self.viewModel.loadLinks(server: server, directory: directory)
  }
}
