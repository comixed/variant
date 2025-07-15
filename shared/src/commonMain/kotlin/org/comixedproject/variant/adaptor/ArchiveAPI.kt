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

package org.comixedproject.variant.adaptor

import co.touchlab.stately.collections.ConcurrentMutableMap
import com.oldguy.common.io.File
import com.oldguy.common.io.ZipFile
import org.comixedproject.variant.model.library.ComicBook
import org.comixedproject.variant.model.library.ComicBookMetadata
import org.comixedproject.variant.model.library.ComicPage
import org.comixedproject.variant.platform.Log

private const val TAG = "ArchiveAPI"
private const val UNKNOWN_METADATA = "Unknown"

public object ArchiveAPI {
    val coverCache = ConcurrentMutableMap<String, ByteArray?>()
    var cachedComic = ""
    val pageCache = ConcurrentMutableMap<String, ByteArray?>()

    suspend fun loadComicBook(archive: File): ComicBook {
        val pages = mutableListOf<ComicPage>()
        var metadata = ComicBookMetadata()

        Log.debug(TAG, "Loading comic archive entries: ${archive.path}")
        try {
            ZipFile(archive).use { zip ->
                zip.map
                    .forEach { entry ->
                        val filename = entry.key
                        Log.debug(TAG, "Found entry: ${filename}")
                        if (filename.endsWith("ComicInfo.xml")) {
                            var content = ""
                            zip.readTextEntry(filename) { text, last ->
                                content += text
                                if (last) {
                                    MetadataAPI.loadMetadata(metadata, content)
                                }
                            }
                        } else if (isImageFile(filename)) {
                            pages.add(ComicPage(filename))
                        }
                    }
            }
        } catch (error: Exception) {
            Log.error(
                TAG,
                "Failed to load comic file entries for ${archive.name}: ${error.message}"
            )
            error.printStackTrace()
        }
        val result = ComicBook(
            archive.fullPath,
            archive.name,
            archive.size.toLong(),
            archive.lastModifiedEpoch,
            metadata,
            pages
        )
        return result
    }

    suspend fun loadCover(comicFilename: String, pageFilename: String): ByteArray? {
        val key = "${comicFilename}:${pageFilename}"

        if (coverCache.contains(key)) {
            Log.debug(TAG, "Retrieving cached cover for comic: ${comicFilename}")
            return coverCache.get(key)
        }

        val image = loadImageFromFile(comicFilename, pageFilename)
        Log.debug(TAG, "Caching cover image: ${key}")
        coverCache.put(key, image)
        Log.debug(TAG, "Returning ${image?.size} bytes for ${key}")
        return image
    }

    suspend fun loadPage(comicFilename: String, pageFilename: String): ByteArray? {
        if (!cachedComic.equals(comicFilename)) {
            Log.debug(TAG, "Resetting page caching for comic: ${comicFilename}")
            pageCache.clear()
            cachedComic = comicFilename
        } else if (pageCache.contains(pageFilename)) {
            Log.debug(TAG, "Retrieving cached page for comic: ${comicFilename}:${pageFilename}")
            return pageCache.get(pageFilename)
        }

        val image = loadImageFromFile(comicFilename, pageFilename)
        Log.debug(TAG, "Caching page for comic: ${pageFilename}")
        pageCache.put(pageFilename, image)
        return image
    }

    private suspend fun loadImageFromFile(comicFilename: String, pageFilename: String): ByteArray? {
        Log.debug(TAG, "Loading image from file: ${comicFilename}:${pageFilename}")
        var result: ByteArray? = null
        try {
            ZipFile(File(comicFilename)).use { zip ->
                zip.map
                    .forEach { entry ->
                        if (entry.key.equals(pageFilename)) {
                            zip.readEntry(entry.value) { _, content, _, last ->
                                if (result != null) {
                                    result = result!! + content
                                } else {
                                    result = content
                                }
                            }
                        }
                    }
            }
        } catch (error: Exception) {
            Log.error(
                TAG,
                "Failed to load comic file entries for ${comicFilename}: ${error.message}"
            )
            error.printStackTrace()
        }

        return result
    }

    private fun isImageFile(filename: String): Boolean {
        return filename.endsWith(".jpg") ||
                filename.endsWith(".jpeg") ||
                filename.endsWith(".gif") ||
                filename.endsWith(".png")
    }
}