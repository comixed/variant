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

package org.comixedproject.variant.android.ui

const val NAVARG_SERVER_ID = "serverId"

/**
 * <code>NavigationScreen</code> define a navigation view used by the application.
 *
 * @author Darryl L. Pierce
 */
enum class NavigationScreen(
    val route: String
) {
    ComicList("comics"),
    Servers("servers"),
    BrowseServer("servers/browse"),
    ItemDetail("items/details"),
    Settings("settings");

    companion object {
        val all = values()
    }
}
