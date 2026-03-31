package com.openkin.presentation.ui.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.openkin.domain.utils.SHORT_TEXT_EXAMPLE
import com.openkin.domain.utils.SINGLE_LINE
import com.openkin.presentation.ui.theme.buttonText
import com.openkin.presentation.ui.theme.dialogListText

@Composable
fun SettingsDialog(
    options: List<String>,
    selectedOption: Int,
    onDismissRequest: () -> Unit,
    onConfirmation: (Int) -> Unit,
    confirmButtonText: String,
    dismissButtonText: String,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
        ) {

            var currentOption by remember { mutableIntStateOf(selectedOption) }

            Column(
                modifier = Modifier.padding(8.dp),
            ) {
                for ((index, option) in options.withIndex()) {
                    key(option) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { currentOption = index },
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            RadioButton(
                                selected = index == currentOption,
                                onClick = { currentOption = index },
                            )
                            Text(
                                text = option,
                                style = MaterialTheme.typography.dialogListText,
                                modifier = Modifier.padding(start = 16.dp),
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    if (dismissButtonText.isNotEmpty()) {
                        TextButton(
                            onClick = { onDismissRequest() },
                            modifier = Modifier.fillMaxWidth(fraction = 0.5F),
                        ) {
                            Text(
                                text = dismissButtonText.uppercase(),
                                style = MaterialTheme.typography.buttonText,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = SINGLE_LINE,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                    if (confirmButtonText.isNotEmpty()) {
                        TextButton(
                            onClick = { onConfirmation(currentOption) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 4.dp),
                        ) {
                            Text(
                                text = confirmButtonText.uppercase(),
                                style = MaterialTheme.typography.buttonText,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = SINGLE_LINE,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SettingsDialogPreview() {
    SettingsDialog(
        options = listOf(SHORT_TEXT_EXAMPLE, SHORT_TEXT_EXAMPLE, SHORT_TEXT_EXAMPLE),
        selectedOption = 1,
        onDismissRequest = {},
        onConfirmation = {},
        confirmButtonText = "Выбрать",
        dismissButtonText = "Отмена",
    )
}
