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

package org.comixedproject.variant.android.ui.links

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import junit.framework.TestCase.assertTrue
import org.comixedproject.variant.shared.model.SERVER_LINK_LIST
import org.comixedproject.variant.shared.model.SERVER_LIST
import org.comixedproject.variant.shared.model.server.ServerLinkType
import org.junit.Rule
import org.junit.Test

class ServerLinkDetailViewKtTest {
    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    val server = SERVER_LIST.get(0)
    val serverLink =
        SERVER_LINK_LIST.filter { entry -> entry.linkType == ServerLinkType.PUBLICATION }.first()

    @Test
    fun serverLinkDetailView_showsTitle() {
        var clicked = false

        composeTestRule.setContent {
            ServerLinkDetailView(server, serverLink, onClose = { clicked = true })
        }

        composeTestRule.onNodeWithTag(TAG_TITLE_TEXT, useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_TITLE_TEXT, useUnmergedTree = true)
            .assertTextEquals(serverLink.title)
    }

    @Test
    fun serverLinkDetailView_showsSubtitle() {
        var clicked = false

        composeTestRule.setContent {
            ServerLinkDetailView(server, serverLink, onClose = { clicked = true })
        }

        composeTestRule.onNodeWithTag(TAG_SUBTITLE_TEXT, useUnmergedTree = true)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_SUBTITLE_TEXT, useUnmergedTree = true)
            .assertTextEquals(serverLink.downloadLink)
    }

    @Test
    fun serverLinkDetailView_close_onClick() {
        var clicked = false

        composeTestRule.setContent {
            ServerLinkDetailView(server, serverLink, onClose = { clicked = true })
        }

        composeTestRule.onNodeWithTag(TAG_CLOSE_BUTTON).performClick()
        assertTrue(clicked)
    }
}