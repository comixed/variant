/*
 * Variant - A digital comic book reading application for the iPad and Android tablets.
 * Copyright (C) 2025, The ComiXed Project
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

package org.comixedproject.variant.android.ui.servers

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.comixedproject.variant.android.model.SERVER_LINK_LIST
import org.comixedproject.variant.android.model.SERVER_LIST
import org.comixedproject.variant.shared.model.server.ServerLinkType
import org.junit.Rule
import org.junit.Test

class BrowseServerViewKtTest {
    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    val server = SERVER_LIST.get(0)
    val serverLink = SERVER_LINK_LIST.filter { it.linkType == ServerLinkType.NAVIGATION }.first()

    @Test
    fun browseServerView_rootDirectory_hasBackNavigation() {
        composeTestRule.setContent {
            BrowseServerView(
                server,
                serverLink.downloadLink,
                serverLink.downloadLink,
                serverLink.title,
                false,
                SERVER_LINK_LIST,
                onLoadDirectory = { _, _ -> })
        }

        composeTestRule.onNodeWithTag(TAG_BACK_NAVIGATION_BUTTON).assertDoesNotExist()
    }

    @Test
    fun browseServerView_childDirectory_hasBackNavigation() {
        composeTestRule.setContent {
            BrowseServerView(
                server,
                serverLink.downloadLink,
                serverLink.directory,
                serverLink.title,
                false,
                SERVER_LINK_LIST,
                onLoadDirectory = { _, _ -> })
        }

        composeTestRule.onNodeWithTag(TAG_BACK_NAVIGATION_BUTTON).assertExists()
    }
}