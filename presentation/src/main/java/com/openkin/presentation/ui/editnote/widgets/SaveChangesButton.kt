package com.openkin.presentation.ui.editnote.widgets

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openkin.presentation.R

@Composable
fun SaveChangesButton(
    onSaveClick: () -> Unit,
    isButtonEnabled: Boolean,
    modifier: Modifier,
) {
    Button(
        onClick = { onSaveClick() },
        enabled = isButtonEnabled,
        shape = RoundedCornerShape(5.dp),
        modifier = modifier,
        colors = ButtonColors(
            containerColor = Color(0xFF04A804),
            disabledContainerColor = ButtonDefaults.buttonColors().disabledContainerColor,
            contentColor = Color.White,
            disabledContentColor = ButtonDefaults.buttonColors().disabledContentColor,
        ),
    ) {
        Text(
            text = stringResource(R.string.edit_note_update_button),
            fontSize = 12.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SaveChangesButtonEnablePreview() {
    SaveChangesButton(onSaveClick = {}, isButtonEnabled = true, modifier = Modifier)
}

@Preview(showBackground = true)
@Composable
fun SaveChangesButtonDisablePreview() {
    SaveChangesButton(onSaveClick = {}, isButtonEnabled = false, modifier = Modifier)
}
