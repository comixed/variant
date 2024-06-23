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

package org.comixedproject.variant.shared.domain

import io.ktor.client.statement.bodyAsText
import korlibs.io.serialization.xml.Xml
import kotlinx.coroutines.coroutineScope
import org.comixedproject.variant.shared.data.FeedAPI
import org.comixedproject.variant.shared.model.server.Link
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.platform.Logger

private const val TAG = "GetFeedData"

public class GetFeedData {
    public suspend fun invokeLoadDirectoryOnServer(
        server: Server,
        directory: String,
        onSuccess: (List<Link>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        try {
            Logger.d(TAG, "invokeLoadDirectoryOnServer: server=${server.name} directory=$directory")
            val result = FeedAPI.loadDirectoryOnServer(server, directory)
            Logger.d(TAG, "Result received: $result")
            val xml = Xml.parse(result.bodyAsText())
            val feed = mutableListOf<Link>()
            for (node in xml.allNodeChildren) {
                val link = parseLink(node, server, directory)

                if (link != null) {
                    feed += link
                }
            }
            coroutineScope { onSuccess(feed) }
        } catch (error: Exception) {
            Logger.e(TAG, "Failed to load directory: $error")
            coroutineScope { onFailure(error) }
        }
    }

    fun parseLink(node: Xml, server: Server, directory: String): Link? {
        if (node.name == "entry") {
            val id = node.allNodeChildren.firstOrNull { it.name == "id" }
            val title = node.allNodeChildren.firstOrNull { it.name == "title" }
            val link = node.allNodeChildren.firstOrNull {
                it.name == "link"
            }

            return Link(
                id = null,
                serverId = server.id!!,
                linkId = id?.text ?: "",
                directory = directory,
                link = link?.attributes?.get("href") ?: "",
                title = title?.text ?: "",
                thumbnailURL = null
            )
        } else {
            return null
        }
    }
}
