package uk.co.bbc.application

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test
import uk.co.bbc.application.helpers.ContentPageHelper
import uk.co.bbc.application.helpers.HomepageHelper


class HomePageTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val homepageHelper = HomepageHelper(composeTestRule)
    private val contentPageHelper = ContentPageHelper(composeTestRule)

    @Test
    fun Scenario1_testHomePageLoadsSuccessfully() {
        // Ensures all critical elements on the home page are visible, confirming the app has loaded correctly
        homepageHelper.verifyUserIsOnHomePage()
    }

    @Test
    fun Scenario2_testRefreshButtonUpdatesTime() {
        // Verifies that the refresh button triggers an update to the displayed timestamp
        // This ensures the refresh functionality works as expected
        homepageHelper.verifyRefreshButtonUpdatesTime()
    }

    @Test
    fun Scenario3_testTopicPickerUpdatesGoToLink() {
        // Confirms that selecting a topic in the topic picker updates the "Go To" link appropriately
        // Ensures that topic selection dynamically changes navigation options
        homepageHelper.verifyTopicPickerUpdatesGoToLink()
    }

    @Test
    fun Scenario4_testNavigationToTechnologyAndBack() {
        // Step 1: Select the "Technology" topic and ensure the "Go To" link updates correctly
        homepageHelper.verifyTopicPickerUpdatesGoToLink("Technology")
        homepageHelper.clickGoToButton()

        // Step 2: Verify the content page for "Technology" is loaded, ensuring the navigation was successful
        contentPageHelper.verifyContentPageLoaded("Technology")

        // Step 3: Validate the ability to scroll to the end of the content, ensuring full page content is accessible
        contentPageHelper.verifyScrollToEndOfPage()

        // Step 4: Navigate back to the home page and ensure the user is returned to the home page successfully
        contentPageHelper.navigateBackToHomePage()
        homepageHelper.verifyUserIsOnHomePage()
    }

    @Test
    fun Scenario5_testSelectTvGuideAndStayOnHomePage() {
        // Step 1: Select the "TV Guide" topic and attempt to navigate via the "Go To" link
        homepageHelper.selectTvGuideAndTapGoTo()

        // Step 2: Confirm that an alert dialog appears, indicating the navigation process is interactive
        homepageHelper.verifyAlertDialogAppears()

        // Step 3: Select the "No" option to stay on the current page
        homepageHelper.selectNoOptionInAlertDialog()

        // Step 4: Ensure the user remains on the home page after canceling navigation
        homepageHelper.verifyUserIsOnHomePage()
    }

    @Test
    fun Scenario6_testSelectTvGuideAndNavigateToContentPage() {
        // Step 1: Select the "TV Guide" topic and attempt to navigate via the "Go To" link
        homepageHelper.selectTvGuideAndTapGoTo()

        // Step 2: Confirm that an alert dialog appears to confirm navigation
        homepageHelper.verifyAlertDialogAppears()

        // Step 3: Select the "Yes" option to proceed to the content page
        homepageHelper.selectYesOptionInAlertDialog()

        // Step 4: Verify the "TV Guide" content page is loaded successfully
        contentPageHelper.verifyContentPageLoaded("TV Guide")

        // Step 5: Navigate back to the home page and confirm successful navigation
        contentPageHelper.navigateBackToHomePage()
        homepageHelper.verifyUserIsOnHomePage()
    }

    @Test
    fun Scenario7_testBreakingNewsButtonAndHandleErrorAlert() {
        // Step 1: Simulate tapping the Breaking News button
        homepageHelper.clickBreakingNewsButton()

        // Step 2: Confirm that an error alert dialog appears, indicating a proper error-handling mechanism
        homepageHelper.verifyErrorAlertDialogAppears()

        // Step 3: Close the error alert dialog by clicking "OK"
        homepageHelper.clickOkInErrorAlertDialog()

        // Step 4: Ensure the user remains on the home page after handling the error alert
        homepageHelper.verifyUserIsOnHomePage()
    }
}
