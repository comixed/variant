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

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import junit.framework.TestCase.assertTrue
import org.comixedproject.prestige.android.PrestigeTheme
import org.comixedproject.prestige.model.library.Library
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

const val TEST_LIBRARY_NAME = "My Test Library"
const val TEST_LIBRARY_URL = "http://myserver.lan:7171/opds"
const val TEST_USERNAME = "comixedreader@localhost"
const val TEST_PASSWORD = "comixedreader"

class LibraryEditViewKtTest {
    @get: Rule
    val composeTestRule = createComposeRule()

    var savedLibrary = Library()
    var cancelClicked = false

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
                    ),
                    onSave = { library -> savedLibrary = library },
                    onCancel = { cancelClicked = true })
            }
        }
    }

    @Test
    fun testScreenComposed() {
        composeTestRule.onNodeWithTag(TAG_LIBRARY_NAME).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_LIBRARY_URL).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_USERNAME).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_PASSWORD).assertIsDisplayed()

        composeTestRule.onNodeWithTag(TAG_LIBRARY_NAME).assert(hasText(TEST_LIBRARY_NAME))
        composeTestRule.onNodeWithTag(TAG_LIBRARY_URL).assert(hasText(TEST_LIBRARY_URL))
        composeTestRule.onNodeWithTag(TAG_USERNAME).assert(hasText(TEST_USERNAME))
        composeTestRule.onNodeWithTag(TAG_PASSWORD).assert(hasText(TEST_PASSWORD))
    }

    @Test
    fun testSaveRecord() {
        composeTestRule.onNodeWithTag(TAG_SAVE).performClick()

        Assert.assertEquals(TEST_LIBRARY_NAME, savedLibrary.name)
        Assert.assertEquals(TEST_LIBRARY_URL, savedLibrary.url)
        Assert.assertEquals(TEST_USERNAME, savedLibrary.username)
        Assert.assertEquals(TEST_PASSWORD, savedLibrary.password)
    }

    @Test
    fun testCancel() {
        composeTestRule.onNodeWithTag(TAG_CANCEL).performClick()

        assertTrue(cancelClicked)
    }
}