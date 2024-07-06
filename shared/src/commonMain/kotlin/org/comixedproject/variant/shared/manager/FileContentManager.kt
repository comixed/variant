package org.comixedproject.variant.shared.manager

import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.platform.Logger

private const val TAG = "FileContentManager"

/**
 * <code>FileContentManager</code> provides services for working with content that has been downloaded from a server.
 *
 * @author Darryl L. Pierce
 */
class FileContentManager {
    /**
     * Checks if the content has already been downloaded.
     *
     * @param server the server
     * @param url the content url
     */
    fun contentFound(server: Server, url: String): Boolean {
        Logger.d(TAG, "Looking for content: server=${server.name} url=${url}")
        return doContentFound(server, url)
    }

    /**
     * Stores the content. If existing content is found then it's overwritten.
     *
     * @param server the server
     * @param url the content url
     * @param content the content
     */
    fun storeContent(server: Server, url: String, content: ByteArray) {
        Logger.d(TAG, "Storing file content: server=${server.name} url=${url} size=${content.size}")
        doStoreContent(server, url, content)
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

expect fun doContentFound(server: Server, url: String): Boolean;

expect fun doStoreContent(server: Server, url: String, content: ByteArray)

expect fun doFetchContent(server: Server, url: String): ByteArray

expect fun doPurgeContent(server: Server)