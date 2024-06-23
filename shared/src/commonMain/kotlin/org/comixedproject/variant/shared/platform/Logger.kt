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

package org.comixedproject.variant.shared.platform

internal expect class Log() {
    fun debug(tag: String, message: String)
    fun warn(tag: String, message: String)
    fun error(tag: String, message: String)
}

public object Logger {
    private val logger = Log()

    public fun d(tag: String, message: String) {
        logger.debug(tag, message)
    }

    public fun w(tag: String, message: String) {
        logger.warn(tag, message)
    }

    public fun e(tag: String, message: String) {
        logger.error(tag, message)
    }
}