package org.comixedproject.variant.shared.model.metadata

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