package org.comixedproject.variant.shared.manager

import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.model.server.ServerLink
import org.comixedproject.variant.shared.platform.Logger

private const val TAG = "FileContentManager"

/**
 * <code>FileContentManager</code> provides services for working with content that has been downloaded from a server.
 *
 * @author Darryl L. Pierce
 */
class FileContentManager {
    /**
     * Checks if the server link has already been downloaded.
     *
     * @param server the server
     * @param serverLink the server link
     */
    fun contentFound(server: Server, serverLink: ServerLink): Boolean {
        Logger.d(
            TAG,
            "Looking for content: server=${server.name} serverLink=${serverLink.serverLinkId}"
        )
        return doContentFound(server, createServerLinkFilename(serverLink))
    }

    private fun createServerLinkFilename(serverLink: ServerLink): String {
        return "serverLink-${serverLink.serverLinkId!!}"
    }

    /**
     * Checks if the content has already been downloaded.
     *
     * @param server the server
     * @param filename the filename
     */
    fun contentFound(server: Server, filename: String): Boolean {
        Logger.d(TAG, "Looking for content: server=${server.name} filename=${filename}")
        return doContentFound(server, filename)
    }

    /**
     * Stores the content. If existing content is found then it's overwritten.
     *
     * @param server the server
     * @param url the content url
     * @param content the content
     * @return the filename
     */
    fun storeContent(server: Server, serverLink: ServerLink, content: ByteArray): String {
        Logger.d(
            TAG,
            "Storing file content: server=${server.name} serverLink=${serverLink.serverLinkId} size=${content.size}"
        )
        return doStoreContent(server, "serverLink-${serverLink.serverLinkId!!}", content)
    }

    /**
     * Fetches previously downloaded content if found. Otherwise an empty array is returned.
     *
     * @param server the server
     * @param url the content url
     */
    fun fetchContent(server: Server, url: String): ByteArray {
        Logger.d(TAG, "Fetching file content: server=${server.name} url=${url}")
        return doFetchContent(server, url)
    }

    /**
     * Deletes all content for a given server.
     *
     * @param server the server
     */
    fun purgeContent(server: Server) {
        Logger.d(TAG, "Purging file content: server=${server.name}")
        doPurgeContent(server)
    }
}

expect fun doContentFound(server: Server, filename: String): Boolean

expect fun doStoreContent(server: Server, filename: String, content: ByteArray): String

expect fun doFetchContent(server: Server, filename: String): ByteArray

expect fun doPurgeContent(server: Server)