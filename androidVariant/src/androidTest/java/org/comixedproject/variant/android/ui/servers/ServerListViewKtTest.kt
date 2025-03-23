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
import junit.framework.TestCase.assertTrue
import org.comixedproject.variant.android.model.SERVER_LIST
import org.junit.Rule
import org.junit.Test

class ServerListViewKtTest {
    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    @Test
    fun serverListView_addButton_clicked() {
        var clicked = false
        composeTestRule.setContent {
            ServerListView(
                SERVER_LIST,
                onAddServer = { clicked = true },
                onEditServer = { },
                onDeleteServer = { },
                onBrowseServer = { _ -> })
        }

        composeTestRule.onNodeWithTag(TAG_ADD_BUTTON).performClick()
        assertTrue(clicked)
    }
}