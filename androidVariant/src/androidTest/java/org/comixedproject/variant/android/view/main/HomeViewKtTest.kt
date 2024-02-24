package org.comixedproject.variant.android.view.main

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.comixedproject.variant.android.VariantTheme
import org.junit.Rule
import org.junit.Test

class HomeViewKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun testServerNavigatorHasTitle() {
        composeTestRule.setContent {
            VariantTheme {
                HomeView()
            }
        }

        composeTestRule.onNodeWithTag(TAG_TITLE_TEXT).assertExists()
    }

    @Test
    fun testServerNavigatorHasBottomBar() {
        composeTestRule.setContent {
            VariantTheme {
                HomeView()
            }
        }

        composeTestRule.onNodeWithTag(TAG_BOTTOM_BAR).assertExists()
    }

    @Test
    fun testServerNavigatorHasServerNavigator() {
        composeTestRule.setContent {
            VariantTheme {
                HomeView()
            }
        }

        composeTestRule.onNodeWithTag(TAG_SERVER_NAVIGATOR).assertExists()
    }
}