/*
 * Variant - A digital comic book reading application for iPad, Android, and desktops.
 * Copyright (C) 2023, The ComiXed Project
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

package org.comixedproject.variant.model

/**
 * <code>ServerColorOption</code> defines a type representing the color indicator for a server.
 *
 * @author Darryl L. Pierce
 */
data class ServerColorOption(
    val name: String,
    val hex: String
) {
    companion object {
        val COLORS = listOf(
            ServerColorOption("WHITE", "#FFFFFF"),
            ServerColorOption("RED", "#E57373"),
            ServerColorOption("PINK", "#F06292"),
            ServerColorOption("PURPLE", "#CE93D8"),
            ServerColorOption("BLUE", "#2196F3"),
            ServerColorOption("CYAN", "#00ACC1"),
            ServerColorOption("TEAL", "#26A69A"),
            ServerColorOption("GREEN", "#4CAF50"),
            ServerColorOption("LIGHT GREEN", "#8BC34A"),
            ServerColorOption("LIME", "#CDDC39"),
            ServerColorOption("YELLOW", "#FFEB3B"),
            ServerColorOption("ORANGE", "#FF9800"),
            ServerColorOption("BROWN", "#BCAAA4"),
            ServerColorOption("GREY", "#9E9E9E"),
        )
        val DEFAULT = COLORS[0]

        fun forName(name: String): ServerColorOption? {
            val result = COLORS.find { it.name == name }
            return result ?: DEFAULT
        }
    }
}