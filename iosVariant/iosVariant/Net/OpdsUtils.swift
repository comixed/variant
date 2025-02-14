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

import Foundation
import ReadiumOPDS
import ReadiumShared
import Variant

func loadServerLinks(
  server: Server, directory: String, onSuccess: ([ServerLink]) -> Void, onFailure: () -> Void
) async {
  let httpClient = createOpdsHttpClient(server: server)
  let assetRetriever = AssetRetriever(httpClient: httpClient)
  let url = URL(string: directory, relativeTo: URL(string: server.url))
  print("Preparing to load url: \(url!.absoluteString)")

  switch await assetRetriever.retrieve(url: (url!.anyURL.absoluteURL!)) {
  case .success(let success):
    print("SUCCESS!")
  case .failure(let failure):
    print("FAILURE! \(failure.localizedDescription)")
  }
}
