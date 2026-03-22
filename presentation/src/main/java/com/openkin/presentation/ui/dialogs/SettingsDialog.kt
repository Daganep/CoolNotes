package com.openkin.presentation.ui.dialogs

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

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
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {

            var currentOption by remember { mutableIntStateOf(selectedOption) }

            Column {
                for ((index, option) in options.withIndex()) {
                    key(option) {
                        Row {
                            RadioButton(
                                selected = index == currentOption,
                                onClick = { currentOption = index },
                            )
                            Text(
                                text = option,
                                style = MaterialTheme.typography.bodyLarge,
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
                                text = dismissButtonText,
                                style = MaterialTheme.typography.headlineLarge,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                    if (confirmButtonText.isNotEmpty()) {
                        TextButton(
                            onClick = { onConfirmation(currentOption) },
                            modifier = Modifier.fillMaxWidth().padding(start = 4.dp),
                        ) {
                            Text(
                                text = confirmButtonText,
                                style = MaterialTheme.typography.headlineLarge,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
        }
    }
}
