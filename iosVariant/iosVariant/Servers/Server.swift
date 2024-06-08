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

struct Server: Identifiable, Hashable, Codable {
    let id: Int
    var name: String
    var url: String
    var username: String
}

var servers = [
    Server(id: 1, name: "CX Server 1", url: "http://www.comixedproject.org:7171/opds", username: "reader@comixedproject.org"),
    Server(id: 2, name: "CX Server 2", url: "http://www.comixedproject.org:7171/opds", username: "reader@comixedproject.org"),
    Server(id: 3, name: "CX Server 3", url: "http://www.comixedproject.org:7171/opds", username: "reader@comixedproject.org"),
    Server(id: 4, name: "CX Server 4", url: "http://www.comixedproject.org:7171/opds", username: "reader@comixedproject.org"),
    Server(id: 5, name: "CX Server 5", url: "http://www.comixedproject.org:7171/opds", username: "reader@comixedproject.org")
]
