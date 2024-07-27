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

package org.comixedproject.variant.shared.data

import kotlinx.coroutines.coroutineScope
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.platform.Logger

private const val TAG = "OpdsFeedData"

public class OpdsFeedData {
    public suspend fun getDirectoryContent(
        server: Server,
        directory: String,
        onSuccess: (Any) -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        try {
            Logger.d(TAG, "Loading directory content: server=${server.name} directory=$directory")
            val result = FeedAPI.loadDirectoryOnServer(server, directory)
            Logger.d(TAG, "Parsing OPDS content")
            coroutineScope {
                onSuccess(result)
            }
        } catch (error: Exception) {
            Logger.e(TAG, "Failed to load directory contents: $error")
            coroutineScope {
                {
                    onFailure(error)
                }
            }
        }
    }
}
