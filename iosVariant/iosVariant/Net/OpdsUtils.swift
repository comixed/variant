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

import ReadiumOPDS
@preconcurrency import Variant

private let TAG = "OpdsUtils"

func loadServerLinks(
  server: Server, directory: String, onSuccess: @escaping ([ServerLink]) -> Void,
  onFailure: @escaping () -> Void
) async {
  Log().debug(
    tag: TAG, message: "Loading directory from server: server=\(server.name) directory=\(directory)"
  )
  let url = URL(string: directory, relativeTo: URL(string: server.url))
  var request = URLRequest(url: url!)
  if server.username.isEmpty == false && server.password.isEmpty == false {
    Log().debug(
      tag: TAG,
      message:
        "Setting authentication heading: username=\(server.username) password=\(server.password.masked)"
    )
    let authorization = NetworkUtilsKt.encodeCredentials(
      username: server.username, password: server.password)
    request.setValue("Basic \(authorization)", forHTTPHeaderField: "Authorization")
  }
  Log().debug(tag: TAG, message: "Fetching url: \(String(describing: request.url))")
  let task = URLSession.shared.dataTask(with: request) { data, response, error in
    guard let data = data, let response = response else {
      Log().error(tag: TAG, message: "Unable to download feed")
      onFailure()
      return
    }

    if let parseData = try? OPDS1Parser.parse(xmlData: data, url: url!, response: response) {
      var linkList: [ServerLink] = []
      if let navigations = parseData.feed?.navigation {
        Log().debug(tag: TAG, message: "Processing \(navigations.count) links")
        for navigation in navigations {
          if let title = navigation.title {
            linkList.append(
              ServerLink(
                serverLinkId: nil, serverId: Int64(truncating: server.serverId!),
                directory: directory,
                identifier: "",
                title: title, coverUrl: "", downloadLink: navigation.href,
                linkType: ServerLinkType.navigation, downloaded: false)
            )
          }
        }
      }

      if let publications = parseData.feed?.publications {
        Log().debug(tag: TAG, message: "Processing \(publications.count) publications)")
        for publication in publications {
          let identifier = publication.metadata.identifier ?? ""
          let title = publication.metadata.title ?? ""
          let coverUrl =
            publication.links.filter { ($0.mediaType?.type ?? "") == "image" }.first?.href ?? ""
          let downloadLink =
            publication.links.filter { ($0.mediaType?.subtype ?? "") == "comicbook" }.first?.href
            ?? ""
          linkList.append(
            ServerLink(
              serverLinkId: nil, serverId: Int64(truncating: server.serverId!),
              directory: directory,
              identifier: identifier,
              title: title, coverUrl: coverUrl, downloadLink: downloadLink,
              linkType: ServerLinkType.publication, downloaded: false
            ))
        }
      }
      onSuccess(linkList)
    }
  }
  Log().debug(tag: TAG, message: "Initiating OPDS request")
  task.resume()
}

extension String {
  var masked: String {
    self.enumerated().map({ (index, ch) in
      if index == 0
        || index == self.count - 1
      {
        return String(ch)
      }
      return "x"
    }).joined()
  }
}
