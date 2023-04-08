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

package org.comixedproject.prestige.android.ui.library

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.comixedproject.prestige.android.PrestigeTheme
import org.comixedproject.prestige.model.library.Library
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

const val TEST_LIBRARY_NAME = "My Test Library"
const val TEST_LIBRARY_URL = "http://myserver.lan:7171/opds"
const val TEST_USERNAME = "comixedreader@localhost"
const val TEST_PASSWORD = "comixedreader"

@RunWith(AndroidJUnit4::class)
class LibraryEditViewKtTest {
    @get: Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            PrestigeTheme {
                LibraryEditView(
                    library = Library(
                        name = TEST_LIBRARY_NAME,
                        url = TEST_LIBRARY_URL,
                        username = TEST_USERNAME,
                        password = TEST_PASSWORD
                    ), onSave = { name, url, username, password -> {} }, onCancel = { /*TODO*/ })
            }
        }
    }

    @Test
    fun testVerifyScreenComposed() {
        composeTestRule.onNodeWithTag(TAG_LIBRARY_NAME).assertExists()
        composeTestRule.onNodeWithTag(TAG_LIBRARY_URL).assertExists()
        composeTestRule.onNodeWithTag(TAG_USERNAME).assertExists()
        composeTestRule.onNodeWithTag(TAG_PASSWORD).assertExists()

        composeTestRule.onNodeWithTag(TAG_LIBRARY_NAME).performTextInput(TEST_LIBRARY_NAME)
        composeTestRule.onNodeWithTag(TAG_LIBRARY_URL).performTextInput(TEST_LIBRARY_URL)
        composeTestRule.onNodeWithTag(TAG_USERNAME).performTextInput(TEST_USERNAME)
        composeTestRule.onNodeWithTag(TAG_PASSWORD).performTextInput(TEST_PASSWORD)
    }
}