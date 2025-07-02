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

package org.comixedproject.variant.model.library

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import nl.adaptivity.xmlutil.serialization.XmlValue

@Serializable
@XmlSerialName(value = "ComicInfo")
data class ComicInfo(
    @XmlSerialName(value = "Publisher")
    val publisher: ComicInfoElement = ComicInfoElement(),

    @XmlSerialName(value = "Series")
    val series: ComicInfoElement = ComicInfoElement(),

    @XmlSerialName(value = "Volume")
    val volume: ComicInfoElement = ComicInfoElement(),

    @XmlSerialName(value = "Number")
    val issueNumber: ComicInfoElement = ComicInfoElement(),

    @XmlSerialName(value = "Title")
    val title: ComicInfoElement = ComicInfoElement(),

    @XmlSerialName(value = "Summary")
    val summary: ComicInfoElement = ComicInfoElement(),

    @XmlSerialName(value = "Note")
    val notes: ComicInfoElement = ComicInfoElement(),

    @XmlSerialName(value = "Year")
    val year: Int = 0,

    @XmlSerialName(value = "Month")
    val month: Int = 0,
)

@Serializable
data class ComicInfoElement(@XmlValue val value: String = "")