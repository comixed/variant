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

package org.comixedproject.variant.android.viewmodel

import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.comixedproject.variant.android.koin
import org.comixedproject.variant.shared.data.ServerLinkRepository
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.model.server.ServerLink
import org.comixedproject.variant.shared.model.server.ServerLinkType
import org.comixedproject.variant.shared.platform.Logger
import org.readium.r2.opds.OPDS1Parser
import org.readium.r2.shared.util.http.DefaultHttpClient

private const val TAG = "LinkViewModel"

/**
 * <code>ServerLinkViewModel</code> provides a view model for the links currently displayed for a server.
 *
 * @author Darryl L. Pierce
 */
class ServerLinkViewModel : ViewModel() {
    private val serverLinkRespository: ServerLinkRepository = koin.get()

    private val _displayLinksFlow: MutableStateFlow<List<ServerLink>> by lazy {
        MutableStateFlow(
            serverLinkRespository.serverLinks,
        )
    }
    val displayLinkList = _displayLinksFlow.asStateFlow()

    var directory: String = ""

    fun loadServerDirectory(
        server: Server,
        url: String,
        reload: Boolean,
    ) {
        viewModelScope.launch {
            doLoadServerDirectory(server, url, reload)
        }
    }

    suspend fun doLoadServerDirectory(
        server: Server,
        url: String,
        reload: Boolean,
    ) {
        Logger.d(
            TAG,
            "Loading url: server=${server.name} url=$url reload=$reload",
        )
        val credentials =
            Base64.encodeToString(
                "${server.username}:${server.password}".toByteArray(),
                Base64.DEFAULT,
            )
        val headers = HashMap<String, String>()
        headers.put("Authorization", "Basic $credentials")
        val httpClient =
            DefaultHttpClient(
                userAgent = "CX-Variant",
                additionalHeaders = headers,
            )
        val parser = OPDS1Parser.parseUrlString(url, httpClient)
        val feed = parser.getOrNull()?.feed
        if (feed == null) {
            Logger.d(TAG, "No feed elements retrieved")
        } else {
            val links: MutableList<ServerLink> = mutableListOf()
            if (!feed.navigation.isEmpty()) {
                feed.navigation.forEach { link ->
                    links.add(
                        ServerLink(
                            null,
                            server.serverId!!,
                            url,
                            "",
                            link.title ?: link.href,
                            link.href,
                            ServerLinkType.NAVIGATION,
                        ),
                    )
                }
            }
            feed.publications.forEach { publication ->
                val identifier = publication.metadata.identifier ?: ""
                val title = publication.metadata.title
                val link =
                    publication.links
                        .filter { link ->
                            (link.type ?: "").startsWith("application/")
                        }.firstOrNull()
                        ?.href ?: ""

                links.add(
                    ServerLink(
                        null,
                        server.serverId!!,
                        url,
                        identifier,
                        title,
                        link,
                        ServerLinkType.PUBLICATION,
                    ),
                )
            }
            serverLinkRespository.saveLinksForServer(server, url, links)
            viewModelScope.launch {
                _displayLinksFlow.emit(links)
            }
        }
    }
}
