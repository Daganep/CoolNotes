package com.openkin.presentation.ui.editnote.widgets

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openkin.domain.utils.SINGLE_LINE
import com.openkin.presentation.R
import com.openkin.presentation.ui.theme.buttonText
import com.openkin.presentation.ui.theme.green
import com.openkin.presentation.ui.theme.white
import com.openkin.presentation.utils.toSp

@Composable
fun SaveChangesButton(
    onSaveClick: () -> Unit,
    isButtonEnabled: Boolean,
    modifier: Modifier,
) {
    val density = LocalDensity.current
    Button(
        onClick = { onSaveClick() },
        enabled = isButtonEnabled,
        shape = RoundedCornerShape(5.dp),
        modifier = modifier,
        colors = ButtonColors(
            containerColor = green,
            disabledContainerColor = ButtonDefaults.buttonColors().disabledContainerColor,
            contentColor = white,
            disabledContentColor = ButtonDefaults.buttonColors().disabledContentColor,
        ),
    ) {
        Text(
            text = stringResource(R.string.edit_note_update_button),
            style = MaterialTheme.typography.buttonText,
            lineHeight = 12.dp.toSp(density),
            maxLines = SINGLE_LINE,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SaveChangesButtonEnablePreview() {
    SaveChangesButton(onSaveClick = {}, isButtonEnabled = true, modifier = Modifier)
}

@Preview(showBackground = true)
@Composable
private fun SaveChangesButtonDisablePreview() {
    SaveChangesButton(onSaveClick = {}, isButtonEnabled = false, modifier = Modifier)
}
