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

package org.comixedproject.variant.shared.presentation

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.comixedproject.variant.shared.domain.GetFeedData
import org.comixedproject.variant.shared.domain.LinkFeedData
import org.comixedproject.variant.shared.model.server.Server

private const val TAG = "FeedPresenter"

class FeedPresenter(private val feedData: GetFeedData) {
    public fun loadDirectoryOnServer(
        server: Server,
        directory: String,
        feed: LinkFeedData
    ) {
        MainScope().launch {
            feedData.invokeLoadDirectoryOnServer(server, directory, onSuccess = { links ->
                feed.onNewLinksReceived(server, directory, links, null)
            }, onFailure = { error ->
                feed.onNewLinksReceived(server, directory, emptyList(), error)
            })
        }
    }
}