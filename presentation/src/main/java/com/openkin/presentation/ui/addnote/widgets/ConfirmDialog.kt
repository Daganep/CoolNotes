package com.openkin.presentation.ui.addnote.widgets

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.openkin.domain.utils.SHORT_TEXT_EXAMPLE
import com.openkin.presentation.R

@Composable
fun ConfirmDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogText: String,
    confirmButtonText: String,
    dismissButtonText: String,
    @DrawableRes iconId: Int,
) {
    Dialog(onDismissRequest = {onDismissRequest()}) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(iconId),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 32.dp, bottom = 8.dp)
                        .size(50.dp),
                )
                Text(
                    text = dialogText,
                    fontWeight = FontWeight.Bold,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    if (dismissButtonText.isNotEmpty()) TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.fillMaxWidth(fraction = 0.5F),
                    ) {
                        Text(dismissButtonText)
                    }
                    if (confirmButtonText.isNotEmpty()) TextButton(
                        onClick = { onConfirmation() },
                        modifier = Modifier.fillMaxWidth().padding(start = 4.dp),
                    ) {
                        Text(confirmButtonText)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ConfirmDialogPreview() {
    ConfirmDialog(
        onDismissRequest = {},
        onConfirmation = {},
        dialogText = SHORT_TEXT_EXAMPLE,
        confirmButtonText = SHORT_TEXT_EXAMPLE,
        dismissButtonText = SHORT_TEXT_EXAMPLE,
        iconId = R.drawable.note_to_bin,
    )
}
