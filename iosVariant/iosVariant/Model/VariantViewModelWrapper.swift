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

@available(iOS 17.0, *)
@Observable
final class VariantViewModelWrapper {
  let viewModel: VariantViewModel = Koin.instance.get()

  private(set) var servers: [Server] = []
  private(set) var server: Server? = nil
  private(set) var directory: String = ""
  private(set) var displayLinks: [AcquisitionLink] = []
  private(set) var allLinks: [AcquisitionLink] = []

  init() {
    viewModel.onServerListUpdated = { [weak self] servers in self?.servers = servers }
    viewModel.onDisplayLinksUpdated = { [weak self] links in self?.displayLinks = links }
    viewModel.onAllLinksUpdated = { [weak self] links in self?.allLinks = links }
  }
}
