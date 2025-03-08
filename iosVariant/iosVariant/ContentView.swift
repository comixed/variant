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
struct ContentView: View {
  private var serverViewModel = ServerViewModelWrapper()
  private var serverLinkViewModel = ServerLinkViewModelWrapper()

  var body: some View {
    HomeView(
      serverList: self.serverViewModel.serverList,
      serverLinkList: self.serverLinkViewModel.serverLinkList,
      onSaveServer: { server in
        self.serverViewModel.saveServer(server: server)
      },
      onDeleteServer: { server in
        self.serverViewModel.deleteServer(server: server)
      },
      onBrowseServer: { server, directory, reload in
        if reload || !self.serverLinkViewModel.hasLinks(server: server, directory: directory) {
          Task {
            await loadServerLinks(
              server: server, directory: directory,
              onSuccess: { links in
                self.serverLinkViewModel.saveLinks(
                  server: server, directory: directory, links: links)
              },
              onFailure: {})
          }
        } else {
          self.serverLinkViewModel.loadLinks(server: server, directory: directory)
        }
      })
  }
}

@available(iOS 17.0, *)
struct ContentView_Previews: PreviewProvider {
  static var previews: some View {
    ContentView()
  }
}
