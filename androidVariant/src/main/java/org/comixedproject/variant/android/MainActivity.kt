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

package org.comixedproject.variant.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.comixedproject.variant.android.view.main.HomeView
import org.comixedproject.variant.model.OPDSServer

class MainActivity : ComponentActivity() {
    val serverList = listOf(
        OPDSServer(
            "1",
            "Server 1",
            "http://comixedproject.org:7171/opds",
            "admin@comixedproject.org",
            "my!password"
        ),
        OPDSServer(
            "2",
            "Server 2",
            "http://comixedproject.org:7171/opds",
            "admin@comixedproject.org",
            "my!password"
        ),
        OPDSServer(
            "3",
            "Server 3",
            "http://comixedproject.org:7171/opds",
            "admin@comixedproject.org",
            "my!password"
        ),
        OPDSServer(
            "4",
            "Server 4",
            "http://comixedproject.org:7171/opds",
            "admin@comixedproject.org",
            "my!password"
        ),
        OPDSServer(
            "5",
            "Server 5",
            "http://comixedproject.org:7171/opds",
            "admin@comixedproject.org",
            "my!password"
        ),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VariantTheme {
                HomeView(serverList)
            }
        }
    }
}