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
data class ServerColorChoice(
    val name: String,
    val hex: String
) {
    companion object {
        val COLORS = listOf(
            ServerColorChoice("WHITE", "#FFFFFF"),
            ServerColorChoice("RED", "#E57373"),
            ServerColorChoice("PINK", "#F06292"),
            ServerColorChoice("PURPLE", "#CE93D8"),
            ServerColorChoice("BLUE", "#2196F3"),
            ServerColorChoice("CYAN", "#00ACC1"),
            ServerColorChoice("TEAL", "#26A69A"),
            ServerColorChoice("GREEN", "#4CAF50"),
            ServerColorChoice("LIGHT GREEN", "#8BC34A"),
            ServerColorChoice("LIME", "#CDDC39"),
            ServerColorChoice("YELLOW", "#FFEB3B"),
            ServerColorChoice("ORANGE", "#FF9800"),
            ServerColorChoice("BROWN", "#BCAAA4"),
            ServerColorChoice("GREY", "#9E9E9E"),
        )
        val DEFAULT = COLORS[0]

        fun forName(name: String): ServerColorChoice? {
            val result = COLORS.find { it.name == name }
            return result ?: DEFAULT
        }
    }
}