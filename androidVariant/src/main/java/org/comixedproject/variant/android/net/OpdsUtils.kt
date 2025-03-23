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

package org.comixedproject.variant.android.net

import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.model.server.ServerLink
import org.comixedproject.variant.shared.model.server.ServerLinkType
import org.readium.r2.opds.OPDS1Parser

private val TAG = "OpdsUtils"

/**
 * Loads the contents from a server's directory.
 *
 * @param server the server
 * @param directory the directory
 * @param onSuccess the callback for success
 */
suspend fun loadServerLinks(
    server: Server,
    directory: String,
    onSuccess: (List<ServerLink>) -> Unit
) {

    val httpClient = createOpdsHttpClient(server)
    val parser = OPDS1Parser.parseUrlString(directory, httpClient)
    val feed = parser.getOrNull()?.feed
    if (feed == null) {
        onSuccess(emptyList())
    } else {
        val links: MutableList<ServerLink> = mutableListOf()
        if (!feed.navigation.isEmpty()) {
            feed.navigation.forEach { link ->
                link.title.let { title ->
                    if (title != null) {
                        links.add(
                            ServerLink(
                                null,
                                server.serverId!!,
                                directory,
                                "",
                                link.title ?: "",
                                "",
                                link.href.toString(),
                                ServerLinkType.NAVIGATION,
                            ),
                        )
                    }
                }
            }
        }
        feed.publications.forEach { publication ->
            val identifier = publication.metadata.identifier ?: ""
            val title = publication.metadata.title ?: ""
            val coverUrl = publication.links.filter { link ->
                (link.mediaType?.type ?: "").equals("image")
            }.firstOrNull()?.href.toString()
            val downloadLink =
                publication.links
                    .filter { link ->
                        (link.mediaType?.subtype ?: "").contains("comicbook")
                    }.firstOrNull()
                    ?.href.toString()

            links.add(
                ServerLink(
                    null,
                    server.serverId!!,
                    directory,
                    identifier,
                    title,
                    coverUrl,
                    downloadLink,
                    ServerLinkType.PUBLICATION,
                ),
            )
        }
        onSuccess(links)
    }
}