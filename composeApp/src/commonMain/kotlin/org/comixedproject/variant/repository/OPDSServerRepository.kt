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

package org.comixedproject.variant.repository;

import io.github.aakira.napier.Napier
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.comixedproject.variant.model.OPDSServerEntry

/**
 * <code>OPDSServerRepository</code> provides methods for storing and retrieving instanes of {@link OPDSServerEntry}.
 *
 * @author Darryl L. Pierce
 */
class OPDSServerRepository {
    fun getAllEntries(): List<OPDSServerEntry> {
        Napier.d { "Loading the list of server entries" }
        return listOf(
            OPDSServerEntry(
                "First Entry",
                "http://localhost:7171/opds",
                "comixedreader@localhost",
                "comixedreader",
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            ),
            OPDSServerEntry(
                "Second Entry",
                "http://localhost:7171/opds",
                "comixedreader@localhost",
                "comixedreader",
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            ),
            OPDSServerEntry(
                "Third Entry",
                "http://localhost:7171/opds",
                "comixedreader@localhost",
                "comixedreader",
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            ),
            OPDSServerEntry(
                "Fourth Entry",
                "http://localhost:7171/opds",
                "comixedreader@localhost",
                "comixedreader",
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            ),
            OPDSServerEntry(
                "Fifth Entry",
                "http://localhost:7171/opds",
                "comixedreader@localhost",
                "comixedreader",
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            ),
            OPDSServerEntry(
                "Sixth Entry",
                "http://localhost:7171/opds",
                "comixedreader@localhost",
                "comixedreader",
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            ),
            OPDSServerEntry(
                "Seventh Entry",
                "http://localhost:7171/opds",
                "comixedreader@localhost",
                "comixedreader",
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            ),
            OPDSServerEntry(
                "Eighth Entry",
                "http://localhost:7171/opds",
                "comixedreader@localhost",
                "comixedreader",
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            ),
            OPDSServerEntry(
                "Ninth Entry",
                "http://localhost:7171/opds",
                "comixedreader@localhost",
                "comixedreader",
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            ),
            OPDSServerEntry(
                "Tenth Entry",
                "http://localhost:7171/opds",
                "comixedreader@localhost",
                "comixedreader",
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            )
        );
    }
}