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

package org.comixedproject.variant.ui

/**
 * <code>Screen</code> lists the destination screens in the application.
 *
 * @author Darryl L. Pierce
 */
sealed class Screen(val title: String, val route: String) {
    companion object {
        fun fromRoute(route: String?): Screen {
            return when (route) {
                ServerList.route -> ServerList
                ServerAdd.route -> ServerAdd
                ServerEdit.route -> ServerEdit
                else -> ServerList
            }
        }
    }

    object ServerList : Screen("label.server.list", "server.list")
    object ServerAdd : Screen("label.server.add", "server.add")
    object ServerEdit : Screen("label.server.edit", "server.edit")
}
