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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.comixedproject.variant.android.VariantApp
import org.comixedproject.variant.android.koin
import org.comixedproject.variant.shared.data.ServerLinkRepository
import org.comixedproject.variant.shared.manager.FileContentManager
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.model.server.ServerLink
import org.comixedproject.variant.shared.model.server.ServerLinkType
import org.comixedproject.variant.shared.platform.Logger
import org.readium.r2.opds.OPDS1Parser
import org.readium.r2.shared.util.AbsoluteUrl
import org.readium.r2.shared.util.asset.AssetRetriever
import org.readium.r2.shared.util.format.FormatHints
import org.readium.r2.shared.util.http.DefaultHttpClient
import org.readium.r2.shared.util.http.HttpClient

private const val TAG = "LinkViewModel"

/**
 * <code>ServerLinkViewModel</code> provides a view model for the links currently displayed for a server.
 *
 * @author Darryl L. Pierce
 */
class ServerLinkViewModel : ViewModel() {
    private val serverLinkRepository: ServerLinkRepository = koin.get()
    private val fileContentManager: FileContentManager = koin.get()

    private val _serverLinkListFlow: MutableStateFlow<List<ServerLink>> by lazy {
        MutableStateFlow(
            serverLinkRepository.serverLinks,
        )
    }
    val serverLinkList = _serverLinkListFlow.asStateFlow()

    var directory: String = ""

    /**
     * Retrieves the contents for a directory on a server.
     *
     * If the contents are found locally and the reload flag is false, then no data is retrieved.
     *
     * If the contents are not found locally, or if the reload flag is set, then the contents are fetched from the server.
     *
     * @param server the server
     * @param url the directory
     * @param reload
     */
    fun loadServerDirectory(
        server: Server,
        url: String,
        reload: Boolean,
    ) {
        Logger.d(
            TAG,
            "Loading directory: reload=${reload} server=${server.name} url=${url}"
        )
        viewModelScope.launch {
            doLoadServerDirectory(server, url, reload)
        }
    }

    suspend fun doLoadServerDirectory(
        server: Server,
        url: String,
        reload: Boolean,
    ) {
        Logger.d(TAG, "Checking for existing links: serverId=${server.serverId} directory=${url}")
        val noLinkFound =
            serverLinkRepository.serverLinks
                .filter { link -> link.serverId == server.serverId }
                .filter { link -> link.directory == url }
                .isEmpty()

        if (reload || noLinkFound) {
            Logger.d(
                TAG,
                "Loading url: server=${server.name} url=$url reload=$reload",
            )
            val httpClient = doCreateHttpClient(server)
            val parser = OPDS1Parser.parseUrlString(url, httpClient)
            val feed = parser.getOrNull()?.feed
            if (feed == null) {
                Logger.d(TAG, "No feed elements retrieved")
            } else {
                val links: MutableList<ServerLink> = mutableListOf()
                if (!feed.navigation.isEmpty()) {
                    feed.navigation.forEach { link ->
                        if (link.title == null) {
                            Logger.w(TAG, "Link has no title: ${link.href}")
                        } else {
                            links.add(
                                ServerLink(
                                    null,
                                    server.serverId!!,
                                    url,
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
                            url,
                            identifier,
                            title,
                            coverUrl,
                            downloadLink,
                            ServerLinkType.PUBLICATION,
                        ),
                    )
                }
                serverLinkRepository.saveLinksForServer(server, url, links)
            }
            Logger.d(TAG, "Updating server links")
            viewModelScope.launch {
                _serverLinkListFlow.emit(serverLinkRepository.serverLinks)
            }
        }
    }

    private fun doCreateHttpClient(server: Server): HttpClient =
        DefaultHttpClient(
            userAgent = "CX-Variant",
            callback = HttpCallback(server)
        )

    /**
     * Retrieves a publication from a server.
     *
     * If the publication is already downloaded and the reload flag is false, then no data is retrieved.
     *
     * If the publication is not already downloaded, or if the reload flag is set, then the publication is fetched from the server.
     *
     * @param server the server
     * @param url the directory
     * @param reload
     */
    fun downloadPublication(server: Server, url: String, reload: Boolean) {
        Logger.d(
            TAG,
            "Loading publication: reload=${reload} publication: server=${server.name} url=${url}"
        )

        viewModelScope.launch {
            doDownloadPublication(server, url, reload)
        }
    }

    suspend fun doDownloadPublication(server: Server, url: String, reload: Boolean) {
        if (!reload) {
            Logger.d(TAG, "Skipping download: content found locally")
        } else {
            Logger.d(TAG, "Fetching content")
            val httpClient = doCreateHttpClient(server)
            val assetRetriever = AssetRetriever(
                contentResolver = VariantApp.appContext.contentResolver,
                httpClient = httpClient
            )
            val absoluteUrl = AbsoluteUrl(url)
            Logger.d(TAG, "Retrieving asset: ${absoluteUrl}")

            val asset = assetRetriever.retrieve(
                absoluteUrl!!,
                FormatHints(
                    listOf(
                        "application/vnd.comicbook+zip",
                        "application/vnd.comicbook+rar",
                        "application/vnd.comicbook+octet-stream",
                    ), listOf("CBZ", "CBR", "CB7", "cbz", "cbr", "cb7")
                )
            )
                .onSuccess { thing ->
                    Logger.d(TAG, "Got my thing! ${thing}")
                }
                .onFailure { error ->
                    Logger.e(TAG, "Failed to open comic book: ${error}")
                }
        }
    }
}
