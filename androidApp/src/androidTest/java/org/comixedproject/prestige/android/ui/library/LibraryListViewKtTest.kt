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
import androidx.compose.ui.test.performClick
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertSame
import junit.framework.TestCase.assertTrue
import org.comixedproject.prestige.android.PrestigeTheme
import org.comixedproject.prestige.android.ui.LibraryListView
import org.comixedproject.prestige.android.ui.TAG_REMOVE_BUTTON
import org.comixedproject.prestige.model.library.Library
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LibraryListViewKtTest {
    @get: Rule
    val composeTestRule = createComposeRule()

    var removeClicked = false
    var removedLibrary: Library? = null

    @Before
    fun setUp() {
        composeTestRule.setContent {
            PrestigeTheme {
                LibraryListView(libraries = SampleData.libraries,
                    onAddLibrary = {},
                    onRemoveLibrary = { library ->
                        removeClicked = true
                        removedLibrary = library
                    })
            }
        }
    }

    @Test
    fun testRemoveLibrary() {
        val library = SampleData.libraries[0]

        composeTestRule.onNodeWithTag(TAG_REMOVE_BUTTON + library.libraryId).performClick()

        assertTrue(removeClicked)
        assertNotNull(removedLibrary)
        assertSame(library, removedLibrary)
    }
}