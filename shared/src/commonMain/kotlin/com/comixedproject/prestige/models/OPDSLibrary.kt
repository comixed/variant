/*
 * Prestige - A digital comic book reader.
 * Copyright (C) 2022, The ComiXed Project
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

package com.comixedproject.prestige.models

/**
 * <code>OPDSLibrary</code> represents a single library.
 *
 * @author Darryl L. Pierce
 */
data class OPDSLibrary (
    val name: String = "",
    val url: String = "",
    val username: String = "",
    val password: String = "") {

    companion object {
        var sampleData: List<OPDSLibrary> = listOf<OPDSLibrary>(
            OPDSLibrary("Library 1", "http://localhost:7171/library1", "admin1", "password1"),
            OPDSLibrary("Library 2", "http://localhost:7171/library2", "admin2", "password2"),
            OPDSLibrary("Library 3", "http://localhost:7171/library3", "admin3", "password3"),
            OPDSLibrary("Library 4", "http://localhost:7171/library4", "admin4", "password4"),
            OPDSLibrary("Library 5", "http://localhost:7171/library5", "admin5", "password5"),
        )
    }
}
