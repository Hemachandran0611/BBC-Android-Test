package uk.co.bbc.application.helpers

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Assert
import uk.co.bbc.application.*

/**
 * Helper class for interacting with and verifying elements on the Home Page.
 * Provides reusable methods to validate the home page state and perform actions
 * like navigation and refreshing content.
 */
class HomepageHelper(private val composeTestRule: ComposeTestRule) {

    /**
     * Verifies that a UI element exists and is displayed.
     * Used as a generic helper for assertions on any element.
     *
     * @param tag The test tag for locating the element.
     * @param errorMessage The error message to display if the assertion fails.
     */
    private fun verifyElementExistsAndDisplayed(tag: String, errorMessage: String) {
        composeTestRule.onNodeWithTag(tag)
            .assertExists(errorMessage) // Ensure the element exists
            .assertIsDisplayed() // Ensure the element is visible
    }

    /**
     * Verifies that the user is on the home page by checking the presence
     * of all critical UI elements.
     */
    fun verifyUserIsOnHomePage() {
        // Check the presence of essential elements on the home page
        verifyElementExistsAndDisplayed(TEST_TAG_BBC_LOGO, "User is not on the home page; BBC logo is missing.")
        verifyElementExistsAndDisplayed(TEST_TAG_HOME_PAGE_TITLE, "Page title is missing on the home page.")
        verifyElementExistsAndDisplayed(TEST_TAG_BBC_IMAGE, "Main image is missing on the home page.")
        verifyElementExistsAndDisplayed(TEST_TAG_REFRESH_BUTTON, "Refresh button is missing on the home page.")
        verifyElementExistsAndDisplayed(TEST_TAG_PAGE_SUBTITLE, "Subtitle text is missing on the home page.")
        verifyElementExistsAndDisplayed(TEST_TAG_GO_TO_BUTTON, "Go To button is missing on the home page.")
        verifyElementExistsAndDisplayed(TEST_TAG_BREAKING_NEWS_BUTTON, "Breaking News button is missing on the home page.")
    }

    /**
     * Verifies that the topic picker updates the "Go To" link correctly
     * after selecting a specific topic.
     *
     * @param topic The topic to select in the topic picker (default: "Technology").
     */
    fun verifyTopicPickerUpdatesGoToLink(topic: String = "Technology") {
        // Expand the topic picker
        composeTestRule.onNodeWithTag("current_topic").performClick()

        // Select the given topic from the dropdown menu
        composeTestRule.onNodeWithTag("${TEST_TAG_DROPDOWN_MENU_ITEM}$topic")
            .assertExists("Dropdown menu item '$topic' is missing.") // Verify the topic exists
            .performClick() // Select the topic

        // Verify that the "Go To" link updates to reflect the selected topic
        val goToText = composeTestRule.onNodeWithTag(TEST_TAG_GO_TO_BUTTON)
            .fetchSemanticsNode()
            .config[SemanticsProperties.Text]?.firstOrNull()?.text ?: ""

        Assert.assertEquals(
            "Go to button did not update to 'Go to $topic'.",
            "Go to $topic",
            goToText
        )
    }

    /**
     * Verifies that tapping the refresh button updates the last updated time.
     * Ensures the app correctly refreshes content.
     */
    fun verifyRefreshButtonUpdatesTime() {
        // Capture the initial timestamp displayed on the page
        val initialTime = composeTestRule.onNodeWithTag(TEST_TAG_LAST_UPDATED)
            .assertExists("The last updated time is missing.")
            .fetchSemanticsNode()
            .config[SemanticsProperties.Text]?.joinToString() ?: ""

        // Wait to allow a noticeable time difference
        Thread.sleep(5000)

        // Click on the refresh button to refresh the page
        composeTestRule.onNodeWithTag(TEST_TAG_REFRESH_BUTTON)
            .assertExists("The refresh button is missing.")
            .performClick()

        // Wait for the loading spinner to appear and disappear
        composeTestRule.onNodeWithTag(TEST_TAG_LOADING_SPINNER)
            .assertExists("The loading spinner did not appear.")
            .assertIsDisplayed()

        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithTag(TEST_TAG_LOADING_SPINNER).fetchSemanticsNodes().isEmpty()
        }

        // Capture the updated timestamp
        val updatedTime = composeTestRule.onNodeWithTag(TEST_TAG_LAST_UPDATED)
            .assertExists("The last updated time is missing after refresh.")
            .fetchSemanticsNode()
            .config[SemanticsProperties.Text]?.joinToString() ?: ""

        // Verify that the timestamp has changed
        Assert.assertNotEquals(
            "The last updated time did not change after refreshing.",
            initialTime,
            updatedTime
        )
    }

    /**
     * Simulates clicking the "Go To" button to navigate to a content page.
     */
    fun clickGoToButton() {
        composeTestRule.onNodeWithTag(TEST_TAG_GO_TO_BUTTON)
            .assertExists("Go To button is missing on the home page.")
            .performClick()
    }

    /**
     * Selects "TV Guide" from the topic picker and taps the "Go to TV Guide" link.
     */
    fun selectTvGuideAndTapGoTo() {
        composeTestRule.onNodeWithTag("current_topic").performClick()

        composeTestRule.onNodeWithTag("${TEST_TAG_DROPDOWN_MENU_ITEM}TV Guide")
            .assertExists("Dropdown menu item 'TV Guide' is missing.")
            .performClick()

        composeTestRule.onNodeWithTag(TEST_TAG_GO_TO_BUTTON)
            .assertExists("The 'Go to TV Guide' button is missing.")
            .performClick()
    }

    /**
     * Verifies that an alert dialog appears on the home page.
     */
    fun verifyAlertDialogAppears() {
        verifyElementExistsAndDisplayed(TEST_TAG_ALERT_DIALOG, "Alert dialog is missing.")
    }

    /**
     * Selects the "No" option in an alert dialog.
     */
    fun selectNoOptionInAlertDialog() {
        composeTestRule.onNodeWithTag(TEST_TAG_ALERT_NO_BUTTON)
            .assertExists("The 'No' button in the alert dialog is missing.")
            .performClick()
    }

    /**
     * Selects the "Yes" option in an alert dialog.
     */
    fun selectYesOptionInAlertDialog() {
        composeTestRule.onNodeWithTag(TEST_TAG_ALERT_YES_BUTTON)
            .assertExists("The 'Yes' button in the alert dialog is missing.")
            .performClick()
    }

    /**
     * Simulates tapping the Breaking News button on the home page.
     */
    fun clickBreakingNewsButton() {
        composeTestRule.onNodeWithTag(TEST_TAG_BREAKING_NEWS_BUTTON)
            .assertExists("Breaking News button is missing.")
            .performClick()
    }

    /**
     * Verifies that an error alert dialog appears after clicking the Breaking News button.
     */
    fun verifyErrorAlertDialogAppears() {
        composeTestRule.onNodeWithTag(TEST_TAG_ALERT_DIALOG)
            .assertExists("Error alert dialog is missing.")
            .assertIsDisplayed()
    }

    /**
     * Simulates clicking "OK" in an error alert dialog.
     */
    fun clickOkInErrorAlertDialog() {
        composeTestRule.onNodeWithTag(TEST_TAG_ALERT_CONFIRM_BUTTON)
            .assertExists("OK button in the error alert dialog is missing.")
            .performClick()
    }
}
