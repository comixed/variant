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

package org.comixedproject.variant.shared.manager

import android.content.Context
import korlibs.crypto.md5
import org.comixedproject.variant.shared.VariantAppContext
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.platform.Logger

private const val TAG = "FileContentManager-android"

actual fun doStoreContent(
    server: Server,
    filename: String,
    content: ByteArray
): String {
    if (fileExists(server, filename)) {
        deleteFile(server, filename)
    }

    Logger.d(
        TAG,
        "Storing content: server=${server.name} filename=${filename} size=${content.size}"
    )
    val filename = doCreateFilename(server, filename)
    VariantAppContext.get().openFileOutput(filename, Context.MODE_PRIVATE).use { output ->
        Logger.d(TAG, "Saving file content: ${filename}")
        output.write(content)
        output.close()
    }
    return filename
}

fun deleteFile(server: Server, url: String) {
    if (fileExists(server, url)) {
        val filename = doCreateFilename(server, url)
        Logger.d(TAG, "Deleting file: ${filename}")
        VariantAppContext.get().deleteFile(filename)
    }
}

fun fileExists(server: Server, filename: String): Boolean {
    val filename = doCreateFilename(server, filename)
    Logger.d(TAG, "Checking if file exists: ${filename}")
    return VariantAppContext.get().fileList().contains(filename)
}

actual fun doFetchContent(
    server: Server,
    filename: String
): ByteArray {
    TODO("Not yet implemented")
}

actual fun doPurgeContent(server: Server) {
}

fun doCreateFilename(server: Server, url: String): String {
    val encodedUrl = url.toByteArray().md5()
    return "server-${server.serverId}-${encodedUrl}"
}

actual fun doContentFound(
    server: Server,
    filename: String
): Boolean {
    return fileExists(server, filename)
}