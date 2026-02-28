package com.openkin.presentation.ui.editnote.widgets

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openkin.presentation.R
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
            containerColor = Color(0xFF04A804),
            disabledContainerColor = ButtonDefaults.buttonColors().disabledContainerColor,
            contentColor = Color.White,
            disabledContentColor = ButtonDefaults.buttonColors().disabledContentColor,
        ),
    ) {
        Text(
            text = stringResource(R.string.edit_note_update_button),
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 12.dp.toSp(density),
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
