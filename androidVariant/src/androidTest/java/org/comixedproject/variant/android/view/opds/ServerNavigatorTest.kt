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

package org.comixedproject.variant.android.view.opds

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.comixedproject.variant.android.VariantTheme
import org.comixedproject.variant.model.Server
import org.junit.Rule
import org.junit.Test

class ServerNavigatorTest {
    val serverList = listOf(
        Server(
            "1",
            "Server 1",
            "http://comixedproject.org:7171/opds",
            "admin@comixedproject.org",
            "my!password"
        ),
        Server(
            "2",
            "Server 2",
            "http://comixedproject.org:7171/opds",
            "admin@comixedproject.org",
            "my!password"
        ),
        Server(
            "3",
            "Server 3",
            "http://comixedproject.org:7171/opds",
            "admin@comixedproject.org",
            "my!password"
        ),
        Server(
            "4",
            "Server 4",
            "http://comixedproject.org:7171/opds",
            "admin@comixedproject.org",
            "my!password"
        ),
        Server(
            "5",
            "Server 5",
            "http://comixedproject.org:7171/opds",
            "admin@comixedproject.org",
            "my!password"
        ),
    )

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testServerNavigatorHasServerList() {
        composeTestRule.setContent {
            VariantTheme {
                ServerNavigator(serverList, onSave = {}, onDelete = {})
            }
        }

        composeTestRule.onNodeWithTag(TAG_SERVER_NAVIGATOR_SERVER_LIST).assertExists()
    }
}