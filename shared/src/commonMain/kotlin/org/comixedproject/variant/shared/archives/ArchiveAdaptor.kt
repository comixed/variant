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

package org.comixedproject.variant.shared.archives

import com.oldguy.common.io.File
import com.oldguy.common.io.ZipEntry
import com.oldguy.common.io.ZipFile
import korlibs.io.async.CIO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.comixedproject.variant.shared.model.comics.ComicBook
import org.comixedproject.variant.shared.platform.Log

private val TAG = "ArchiveUtils"

class ArchiveAdaptor() {
    suspend fun loadComicBook(path: String): ComicBook? {
        var result: ComicBook? = null

        try {
            Log.debug(TAG, "Opening archive: ${path}")
            ZipFile(File(path)).use { zip ->
                result = ComicBook(filename = path)

                zip.map.forEach { entry ->
                    val filename = entry.key

                    if (isImageFile(filename)) {
                        result!!.entries.add(filename)
                        if (result!!.coverFilename == "") {
                            Log.debug(TAG, "Found cover image: ${filename}")
                            result!!.coverFilename = filename
                            result!!.coverContent = doLoadCoverContent(zip, entry)
                        }
                    }

                    if (isMetadataFile(filename)) {
                        Log.debug(TAG, "Loading metadata: ${filename}")
                        doLoadMetadata(zip, entry, result)
                    }
                }

            }
        } catch (error: Exception) {
            Log.error(TAG, "Not an archive: ${path}")
        }

        return result
    }

    private suspend fun doLoadCoverContent(
        zip: ZipFile,
        entry: Map.Entry<String, ZipEntry>
    ): ByteArray? {
        var result: ByteArray? = null
        zip.readEntry(entry.value) { _, content, _, last ->
            if (result != null) {
                result = result!! + content
            } else {
                result = content
            }
        }
        return result
    }

    private suspend fun doLoadMetadata(
        zip: ZipFile,
        entry: Map.Entry<String, ZipEntry>,
        comicBook: ComicBook
    ) {
        withContext(Dispatchers.CIO) {
            var context = ""
            zip.readTextEntry(entry.key) { text, last ->
                context = context + text
                if (last) {
                    Log.debug(TAG, "Processing metadata content")
                    loadMetadata(comicBook.metadata, context)
                }
            }
        }
    }

    private fun isImageFile(filename: String): Boolean {
        return filename.lowercase().endsWith("jpg")
                || filename.lowercase().endsWith("jpeg")
                || filename.lowercase().endsWith("gif")
                || filename.lowercase().endsWith("png")
    }

    private fun isMetadataFile(filename: String): Boolean {
        return filename.equals("ComicInfo.xml")
    }
}