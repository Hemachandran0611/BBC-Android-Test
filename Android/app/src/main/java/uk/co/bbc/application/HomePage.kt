package uk.co.bbc.application

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.co.bbc.application.ui.theme.ApplicationTheme
import java.text.SimpleDateFormat
import java.util.Date

// Test tags
const val TEST_TAG_REFRESH_BUTTON = "refresh_button"
const val TEST_TAG_DROPDOWN_MENU = "dropdown_menu"
const val TEST_TAG_DROPDOWN_MENU_ITEM = "dropdown_menu_item_"
const val TEST_TAG_BREAKING_NEWS_BUTTON = "breaking_news_button"
const val TEST_TAG_LOADING_SPINNER = "loading_spinner"
const val TEST_TAG_LAST_UPDATED = "last_updated"
const val TEST_TAG_GO_TO_BUTTON = "go_to_button"
const val TEST_TAG_BBC_LOGO = "bbc_logo"
const val TEST_TAG_BBC_IMAGE = "bbc_image"
const val TEST_TAG_PAGE_SUBTITLE = "page_subtitle"
const val TEST_TAG_HOME_PAGE_TITLE = "home_page_title" // Updated constant

@Composable
fun HomePage(
    onBreakingNewsClick: () -> Unit,
    goToClicked: (String) -> Unit,
    title: String,
    onDropdownItemClick: (String, Int) -> Unit,
    itemPosition: Int,
    onRefreshClick: () -> Unit
) {
    val currentDateAndTime = rememberSaveable { mutableStateOf(Date()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // BBC Logo
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.bbc_logo),
            contentDescription = "BBC Logo",
            modifier = Modifier
                .padding(top = 20.dp)
                .testTag(TEST_TAG_BBC_LOGO)
        )
        Spacer(Modifier.padding(bottom = 50.dp))

        // Page title and refresh button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.page_title),
                fontSize = 35.sp,
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .testTag(TEST_TAG_HOME_PAGE_TITLE) // Updated constant
            )
            RefreshButton {
                onRefreshClick()
                currentDateAndTime.value = Date()
            }
        }

        // Main image
        Image(
            painter = painterResource(R.mipmap.bbc_broadcasting_house_foreground),
            contentDescription = "BBC Logo",
            modifier = Modifier
                .fillMaxWidth()
                .size(250.dp)
                .testTag(TEST_TAG_BBC_IMAGE)
        )
        Subheading(currentDateAndTime.value)

        Spacer(Modifier.padding(bottom = 15.dp))

        // Dropdown and Go-To button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Go to $title",
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clickable { goToClicked(title) }
                    .testTag(TEST_TAG_GO_TO_BUTTON)
            )
            PickerDropdownMenu(onClick = onDropdownItemClick, itemPosition)
        }

        Spacer(Modifier.padding(bottom = 30.dp))

        // Breaking News button
        Footer(onBreakingNewsClick)
    }
}

@Composable
fun Subheading(currentTime: Date) {
    val dayFormat = SimpleDateFormat("dd MMM yyyy")
    val timeFormat = SimpleDateFormat("hh:mm:ss")
    val day = dayFormat.format(currentTime)
    val time = timeFormat.format(currentTime)

    // Last updated text
    Text(
        text = "Last updated: $day at $time",
        modifier = Modifier
            .padding(bottom = 10.dp)
            .testTag(TEST_TAG_LAST_UPDATED)
    )

    // Subtitle text
    Text(
        text = stringResource(R.string.subtitle_text),
        modifier = Modifier
            .padding(horizontal = 45.dp)
            .testTag(TEST_TAG_PAGE_SUBTITLE)
    )
}

@Composable
fun PickerDropdownMenu(onClick: (String, Int) -> Unit, itemPosition: Int) {
    val dropdownExpanded = rememberSaveable { mutableStateOf(false) }
    val topics = listOf("Politics", "UK", "Sport", "Technology", "World", "TV Guide")

    Box(
        modifier = Modifier.padding(end = 16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { dropdownExpanded.value = true }
                .testTag("current_topic")
        ) {
            Text(text = topics[itemPosition])
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
        }
        DropdownMenu(
            modifier = Modifier.testTag(TEST_TAG_DROPDOWN_MENU),
            expanded = dropdownExpanded.value,
            onDismissRequest = { dropdownExpanded.value = false }
        ) {
            topics.forEachIndexed { index, currentCategory ->
                DropdownMenuItem(
                    modifier = Modifier.testTag("$TEST_TAG_DROPDOWN_MENU_ITEM$currentCategory"),
                    onClick = {
                        onClick(currentCategory, index)
                        dropdownExpanded.value = false
                    },
                    text = { Text(text = currentCategory) }
                )
            }
        }
    }
}

@Composable
fun Footer(onBreakingNewsClick: () -> Unit) {
    Button(
        modifier = Modifier.testTag(TEST_TAG_BREAKING_NEWS_BUTTON),
        onClick = onBreakingNewsClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
    ) {
        Text(text = stringResource(R.string.breaking_news))
    }
}

@Composable
fun RefreshButton(onRefreshClick: () -> Unit) {
    Box {
        Icon(
            imageVector = Icons.Filled.Refresh,
            contentDescription = "Refresh",
            tint = Color.Blue,
            modifier = Modifier
                .testTag(TEST_TAG_REFRESH_BUTTON)
                .padding(horizontal = 25.dp)
                .clickable {
                    onRefreshClick()
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    ApplicationTheme {
        HomePage(
            onBreakingNewsClick = { },
            goToClicked = {},
            title = "Politics",
            onDropdownItemClick = { _, _ -> },
            itemPosition = 1,
            onRefreshClick = { }
        )
    }
}
