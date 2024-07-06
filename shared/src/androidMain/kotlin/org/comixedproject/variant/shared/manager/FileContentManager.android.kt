package org.comixedproject.variant.shared.manager

import org.comixedproject.variant.shared.model.server.Server

actual fun doContentFound(
    server: Server,
    url: String
): Boolean {
    return false
}

actual fun doStoreContent(
    server: Server,
    url: String,
    content: ByteArray
) {
}

actual fun doFetchContent(
    server: Server,
    url: String
): ByteArray {
    TODO("Not yet implemented")
}

actual fun doPurgeContent(server: Server) {
}