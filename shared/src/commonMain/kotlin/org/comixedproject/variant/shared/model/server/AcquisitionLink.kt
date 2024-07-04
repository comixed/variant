package org.comixedproject.variant.shared.model.server

class AcquisitionLink(
    val id: String?,
    val serverId: String,
    val linkId: String,
    val directory: String,
    val link: String,
    val title: String,
    val thumbnailURL: String?
) {
}