package uk.co.bbc.application

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.co.bbc.application.ui.theme.ApplicationTheme

const val TEST_TAG_CONTENT_PAGE_TITLE = "content_page_title"
const val TEST_TAG_END_TEXT = "end_of_page_text"
const val TEST_TAG_BACK_BUTTON = "back_button"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentPage(onHomeClick: () -> Unit, title: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = "$title Content Page",
                        modifier = Modifier.testTag(TEST_TAG_CONTENT_PAGE_TITLE)
                    )
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.testTag(TEST_TAG_BACK_BUTTON),
                        onClick = { onHomeClick() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .weight(1f, fill = false),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = title)
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "This is the placeholder content for $title.")
                Spacer(modifier = Modifier.height(20.dp))

                // End-of-page text
                Text(
                    text = "This is the end of the placeholder text.",
                    modifier = Modifier.testTag(TEST_TAG_END_TEXT)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContentPagePreview() {
    ApplicationTheme {
        ContentPage(onHomeClick = {}, title = "Technology")
    }
}
