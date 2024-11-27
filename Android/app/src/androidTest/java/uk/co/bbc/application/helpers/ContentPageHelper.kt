package uk.co.bbc.application.helpers

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import uk.co.bbc.application.TEST_TAG_BACK_BUTTON
import uk.co.bbc.application.TEST_TAG_CONTENT_PAGE_TITLE
import uk.co.bbc.application.TEST_TAG_END_TEXT

/**
 * Helper class for interacting with and verifying elements on the Content Page.
 * This class provides reusable methods to validate the content page state
 * and perform actions like navigating back to the home page.
 */
class ContentPageHelper(private val composeTestRule: ComposeTestRule) {

    /**
     * Verifies that the content page is loaded successfully.
     * This includes checking that the page title and the end-of-page text are displayed.
     *
     * @param title The title of the content page (default is "Technology").
     */
    fun verifyContentPageLoaded(title: String = "Technology") {
        // Verify that the content page title exists and is displayed
        composeTestRule.onNodeWithTag(TEST_TAG_CONTENT_PAGE_TITLE)
            .assertExists("$title content page title is missing.") // Fails if the title is not found
            .assertIsDisplayed() // Ensures the title is visible

        // Verify that the end-of-page text exists and is displayed
        composeTestRule.onNodeWithTag(TEST_TAG_END_TEXT)
            .assertExists("End of page text is missing.") // Fails if the end-of-page text is not found
            .assertIsDisplayed() // Ensures the text is visible
    }

    /**
     * Verifies that the user can scroll to the end of the content page.
     * This method checks that the end-of-page text is visible after scrolling.
     */
    fun verifyScrollToEndOfPage() {
        // Ensure the end-of-page text is visible after scrolling
        composeTestRule.onNodeWithTag(TEST_TAG_END_TEXT)
            .assertIsDisplayed() // Verifies that the end-of-page text is in view
    }

    /**
     * Navigates back to the home page by clicking the back button.
     * This ensures the back button exists and performs the click action.
     */
    fun navigateBackToHomePage() {
        // Locate and click the back button to return to the home page
        composeTestRule.onNodeWithTag(TEST_TAG_BACK_BUTTON)
            .assertExists("Back to Home button is missing.") // Fails if the back button is not found
            .performClick() // Simulates a click on the back button
    }
}
