package uk.co.bbc.application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import uk.co.bbc.application.ui.theme.ApplicationTheme


class MainActivity : ComponentActivity() {

    private val uiState = MutableStateFlow<UiState>(UiState.HomePage(showDialog = false))
    private val onBreakingNewsClick = { uiState.value = UiState.HomePage(showDialog = true) }
    private val onHomeClick = { uiState.value = UiState.HomePage(showDialog = false) }
    private val goToClicked = { title: String ->
        if (title == "TV Guide") {
            uiState.value = UiState.HomePage(
                showDialog = false,
                showTvLicenseDialog = true,
                showLoader = false
            )
        } else {
            uiState.value = UiState.ContentPage(showDialog = false)
        }

    }
    private val onYesClick = {
        uiState.value = UiState.ContentPage(showDialog = false)
    }
    private val onRefreshClick: () -> Unit = {
        uiState.value = UiState.HomePage(showDialog = false, showLoader = true)
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000L)
            uiState.value = UiState.HomePage(showDialog = false, showLoader = false)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            ApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val uiState = uiState.collectAsState()
                    DrawContent(
                        uiState,
                        innerPadding,
                        onHomeClick,
                        onBreakingNewsClick,
                        goToClicked,
                        onRefreshClick,
                        onYesClick
                    )
                }
            }
        }
    }
}


@Composable
fun DrawContent(
    uiState: State<UiState>,
    innerPadding: PaddingValues,
    onHomeClick: () -> Unit,
    onBreakingNewsClick: () -> Unit,
    goToClicked: (String) -> Unit,
    onRefreshClick: () -> Unit,
    onConfirmClick: () -> Unit
) {
    val currentTopic = rememberSaveable { mutableStateOf("Politics") }
    val itemPosition = rememberSaveable { mutableStateOf(0) }
    val newUiState = uiState.value
    when (newUiState) {
        is UiState.HomePage -> {
            HomePage(
                onBreakingNewsClick,
                goToClicked,
                title = currentTopic.value,
                onDropdownItemClick = { topic, position ->
                    currentTopic.value = topic
                    itemPosition.value = position
                },
                itemPosition = itemPosition.value,
                onRefreshClick = onRefreshClick
            )
            if (newUiState.showDialog) {
                AlertMessage(onHomeClick)

            }
            if (newUiState.showLoader) {
                LoadingDialog()
            }
            if (newUiState.showTvLicenseDialog) {
                TvLicenseAlertMessage(onHomeClick, onConfirmClick = onConfirmClick)
            }
        }

        is UiState.ContentPage -> {
            ContentPage(
                onHomeClick = onHomeClick,
                title = currentTopic.value
            )
        }
    }
}

sealed interface UiState {
    data class HomePage(
        val showDialog: Boolean,
        val showTvLicenseDialog: Boolean = false,
        val showLoader: Boolean = false
    ) : UiState

    data class ContentPage(val showDialog: Boolean) : UiState
}

@Composable
fun LoadingDialog() {
    AlertDialog(
        onDismissRequest = { /* Prevent dismissal */ },
        title = null,
        text = {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(modifier = Modifier.testTag(TEST_TAG_LOADING_SPINNER))
                Spacer(modifier = Modifier.height(16.dp))
                Text("Loading, please wait...")
            }
        },
        confirmButton = {} // No confirm button, as it's just a loading indicator
    )
}
