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
import org.comixedproject.variant.android.model.SERVER_LINK_LIST
import org.comixedproject.variant.shared.model.server.ServerLinkType
import org.junit.Rule
import org.junit.Test

class ServerLinkItemViewKtTest {
    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    val navigationServerLink =
        SERVER_LINK_LIST.filter { entry -> entry.linkType == ServerLinkType.NAVIGATION }.first()
    val publicationServerLink =
        SERVER_LINK_LIST.filter { entry -> entry.linkType == ServerLinkType.PUBLICATION }.first()

    @Test
    fun serverLinkItemView_navigationLink_showsNavigationIcon() {
        composeTestRule.setContent {
            ServerLinkListItemView(navigationServerLink, onLoadLink = {}, onShowInfo = {})
        }

        composeTestRule.onNodeWithTag(TAG_NAVIGATION_ICON).assertExists()
        composeTestRule.onNodeWithTag(TAG_NAVIGATION_ICON).assertIsDisplayed()
    }

    @Test
    fun serverLinkItemView_navigationLink_showsTitle() {
        composeTestRule.setContent {
            ServerLinkListItemView(navigationServerLink, onLoadLink = {}, onShowInfo = {})
        }

        composeTestRule.onNodeWithTag(TAG_SERVER_LINK_TITLE, useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag(TAG_SERVER_LINK_TITLE, useUnmergedTree = true)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_SERVER_LINK_TITLE, useUnmergedTree = true)
            .assertTextEquals(navigationServerLink.title)
    }

    @Test
    fun serverLinkItemView_navigationLink_icon_onClick_callsLoadLink() {
        var clicked = false
        composeTestRule.setContent {
            ServerLinkListItemView(
                navigationServerLink,
                onLoadLink = { clicked = true },
                onShowInfo = {})
        }

        composeTestRule.onNodeWithTag(TAG_NAVIGATION_ICON).performClick()
        assertTrue(clicked)
    }

    @Test
    fun serverLinkItemView_navigationLink_onClick_callsLoadLink() {
        var clicked = false
        composeTestRule.setContent {
            ServerLinkListItemView(
                navigationServerLink,
                onLoadLink = { clicked = true },
                onShowInfo = {})
        }

        composeTestRule.onNodeWithTag(TAG_SERVER_LINK_ITEM).performClick()
        assertTrue(clicked)
    }

    @Test
    fun serverLinkItemView_publicationLink_showsPublicationIcon() {
        composeTestRule.setContent {
            ServerLinkListItemView(publicationServerLink, onLoadLink = {}, onShowInfo = {})
        }

        composeTestRule.onNodeWithTag(TAG_PUBLICATION_ICON).assertExists()
        composeTestRule.onNodeWithTag(TAG_PUBLICATION_ICON).assertIsDisplayed()
    }

    @Test
    fun serverLinkItemView_publicationLink_showsTitle() {
        composeTestRule.setContent {
            ServerLinkListItemView(publicationServerLink, onLoadLink = {}, onShowInfo = {})
        }

        composeTestRule.onNodeWithTag(TAG_SERVER_LINK_TITLE, useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag(TAG_SERVER_LINK_TITLE, useUnmergedTree = true)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_SERVER_LINK_TITLE, useUnmergedTree = true)
            .assertTextEquals(publicationServerLink.title)
    }

    @Test
    fun serverLinkItemView_publicationLink_icon_onClick_callsShowInfo() {
        var clicked = false
        composeTestRule.setContent {
            ServerLinkListItemView(
                publicationServerLink,
                onLoadLink = { },
                onShowInfo = { clicked = true })
        }

        composeTestRule.onNodeWithTag(TAG_PUBLICATION_ICON).performClick()
        assertTrue(clicked)
    }

    @Test
    fun serverLinkItemView_publicationLink_onClick_callsLoadLink() {
        var clicked = false
        composeTestRule.setContent {
            ServerLinkListItemView(
                publicationServerLink,
                onLoadLink = { clicked = true },
                onShowInfo = {})
        }

        composeTestRule.onNodeWithTag(TAG_SERVER_LINK_ITEM).performClick()
        assertTrue(clicked)
    }
}