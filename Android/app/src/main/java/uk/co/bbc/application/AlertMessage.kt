package uk.co.bbc.application

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import uk.co.bbc.application.ui.theme.ApplicationTheme

// Test tags
const val TEST_TAG_ALERT_CONFIRM_BUTTON = "alert_confirm_button"
const val TEST_TAG_ALERT_DIALOG = "alert_dialog"
const val TEST_TAG_ALERT_YES_BUTTON = "alert_yes_button"
const val TEST_TAG_ALERT_NO_BUTTON = "alert_no_button"

@Composable
fun AlertMessage(onHomeClick: () -> Unit) {
    AlertDialog(
        onDismissRequest = { },
        title = {
            Text(
                text = stringResource(R.string.error_message),
                modifier = Modifier.testTag(TEST_TAG_ALERT_DIALOG)
            )
        },
        confirmButton = {
            Button(
                modifier = Modifier.testTag(TEST_TAG_ALERT_CONFIRM_BUTTON),
                onClick = onHomeClick
            ) {
                Text(
                    text = "Confirm",
                    color = Color.White
                )
            }
        }
    )
}

@Composable
fun TvLicenseAlertMessage(onHomeClick: () -> Unit, onConfirmClick: () -> Unit) {
    AlertDialog(
        onDismissRequest = { },
        title = {
            Text(
                text = stringResource(R.string.tv_license_message),
                modifier = Modifier.testTag(TEST_TAG_ALERT_DIALOG)
            )
        },
        confirmButton = {
            Button(
                modifier = Modifier.testTag(TEST_TAG_ALERT_YES_BUTTON),
                onClick = onConfirmClick
            ) {
                Text(
                    text = "Yes",
                    color = Color.White
                )
            }
        },
        dismissButton = {
            Button(
                modifier = Modifier.testTag(TEST_TAG_ALERT_NO_BUTTON),
                onClick = onHomeClick
            ) {
                Text(
                    text = "No",
                    color = Color.White
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AlertMessagePreview() {
    ApplicationTheme {
        AlertMessage { }
    }
}

@Preview(showBackground = true)
@Composable
fun TvLicenseAlertMessagePreview() {
    ApplicationTheme {
        TvLicenseAlertMessage({ }) { }
    }
}
