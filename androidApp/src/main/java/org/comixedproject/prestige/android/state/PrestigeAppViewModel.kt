/*
 * Prestige - A digital comic book reading application.
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

package org.comixedproject.prestige.android.state

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import org.comixedproject.prestige.model.library.Library

/**
 * <code>AppViewModel</code> contains the applications runtime state data model.
 *
 * @author Darryl L. Pierce
 */
class PrestigeAppViewModel(application: Application) : AndroidViewModel(application) {
    private var _libraryServers: MutableList<Library> = mutableListOf()
    val libraryServers: List<Library> get() = _libraryServers.toList()

    fun addLibrary(library: Library) {
        _libraryServers.add(library)
    }
}