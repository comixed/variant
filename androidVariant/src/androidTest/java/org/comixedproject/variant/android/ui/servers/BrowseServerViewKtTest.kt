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
import androidx.compose.ui.test.performClick
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.comixedproject.variant.shared.model.SERVER_LINK_LIST
import org.comixedproject.variant.shared.model.SERVER_LIST
import org.comixedproject.variant.shared.model.server.ServerLinkType
import org.junit.Rule
import org.junit.Test

class BrowseServerViewKtTest {
    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    val server = SERVER_LIST.get(0)
    val serverLink = SERVER_LINK_LIST.filter { it.linkType == ServerLinkType.NAVIGATION }.first()

    @Test
    fun browseServerView_withoutParent_rootDirectory_backNavigation_exists() {
        composeTestRule.setContent {
            BrowseServerView(
                server,
                serverLink.downloadLink,
                serverLink.downloadLink,
                serverLink.title,
                false,
                SERVER_LINK_LIST,
                onLoadDirectory = { _, _ -> },
                onStopBrowsing = { })
        }

        composeTestRule.onNodeWithTag(TAG_BACK_NAVIGATION_BUTTON).assertDoesNotExist()
    }

    @Test
    fun browseServerView_withParent_rootDirectory_backNavigation_exists() {
        composeTestRule.setContent {
            BrowseServerView(
                server,
                serverLink.downloadLink,
                serverLink.directory,
                serverLink.title,
                false,
                SERVER_LINK_LIST,
                onLoadDirectory = { _, _ -> },
                onStopBrowsing = { })
        }

        composeTestRule.onNodeWithTag(TAG_BACK_NAVIGATION_BUTTON).assertExists()
    }

    @Test
    fun browseServerView_with_parent_rootDirectory_backNavigation_clicked() {
        var clicked = false
        var directory = ""
        var reload = true


        composeTestRule.setContent {
            BrowseServerView(
                server,
                serverLink.downloadLink,
                serverLink.directory,
                serverLink.title,
                false,
                SERVER_LINK_LIST,
                onLoadDirectory = { dir, rel ->
                    clicked = true
                    directory = dir
                    reload = rel
                },
                onStopBrowsing = { })
        }

        composeTestRule.onNodeWithTag(TAG_BACK_NAVIGATION_BUTTON).performClick()
        assertTrue(clicked)
        assertEquals(serverLink.directory, directory)
        assertFalse(reload)
    }

    @Test
    fun browseServerView_childDirectory_stopNavigating_exists() {
        composeTestRule.setContent {
            BrowseServerView(
                server,
                serverLink.downloadLink,
                serverLink.directory,
                serverLink.title,
                false,
                SERVER_LINK_LIST,
                onLoadDirectory = { _, _ -> },
                onStopBrowsing = { })
        }

        composeTestRule.onNodeWithTag(TAG_STOP_NAVIGATING_BUTTON).assertExists()
    }

    @Test
    fun browseServerView_childDirectory_stopNavigating_clicked() {
        var clicked = false

        composeTestRule.setContent {
            BrowseServerView(
                server,
                serverLink.downloadLink,
                serverLink.downloadLink,
                serverLink.title,
                false,
                SERVER_LINK_LIST,
                onLoadDirectory = { _, _ -> },
                onStopBrowsing = { clicked = true })
        }

        composeTestRule.onNodeWithTag(TAG_STOP_NAVIGATING_BUTTON).performClick()
        assertTrue(clicked)
    }
}