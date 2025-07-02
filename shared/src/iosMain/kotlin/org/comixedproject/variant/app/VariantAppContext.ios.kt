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

package org.comixedproject.variant.app

import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.dataWithBytes
import platform.UIKit.UIImage

actual object VariantAppContext

@kotlinx.cinterop.BetaInteropApi
fun ByteArray.toNSDataOrNull(): NSData? {
    if (this.isEmpty()) return null

    return try {
        this.usePinned {
            NSData.dataWithBytes(
                bytes = it.addressOf(0),
                length = this.size.convert()
            )
        }
    } catch (e: Exception) {
        null
    }
}

@kotlinx.cinterop.BetaInteropApi
fun ByteArray.toUIImage(scale: Double): UIImage? = memScoped {
    var result: UIImage? = null

    toNSDataOrNull()?.let { nsData ->
        result = UIImage.imageWithData(nsData, scale)
    }

    result
}

@kotlinx.cinterop.BetaInteropApi
fun ByteArray.toUIImage(): UIImage? = this.toUIImage(1.0)