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

import nl.adaptivity.xmlutil.XmlDeclMode
import nl.adaptivity.xmlutil.core.XmlVersion
import nl.adaptivity.xmlutil.serialization.XML
import nl.adaptivity.xmlutil.serialization.XmlConfig
import org.comixedproject.variant.shared.model.metadata.ComicInfo
import org.comixedproject.variant.shared.model.metadata.ComicMetadata
import org.comixedproject.variant.shared.platform.Log

private val TAG = "MetadataUtils"

fun loadMetadata(metadata: ComicMetadata, xmlText: String) {
    Log.debug(TAG, "Processing metadata content: ${xmlText}")
    val xml = XML {
        xmlVersion = XmlVersion.XML10
        xmlDeclMode = XmlDeclMode.Auto
        defaultPolicy {
            unknownChildHandler = XmlConfig.IGNORING_UNKNOWN_CHILD_HANDLER
        }
    }
    try {
        val decoded = xml.decodeFromString(ComicInfo.serializer(), xmlText)
        Log.debug(TAG, "Copying metadata data")
        metadata.publisher = decoded.publisher.value
        metadata.series = decoded.series.value
        metadata.volume = decoded.volume.value
        metadata.issueNumber = decoded.issueNumber.value
        metadata.title = decoded.title.value
        metadata.notes = decoded.notes.value
        metadata.summary = decoded.summary.value
        metadata.year = decoded.year
        metadata.month = decoded.month
    } catch (error: Exception) {
        Log.error(TAG, "Failed to extract metadata: ${error.message}")
    }
}