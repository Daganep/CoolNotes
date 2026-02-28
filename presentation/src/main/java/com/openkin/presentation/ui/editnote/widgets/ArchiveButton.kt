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
fun ArchiveButton(
    onButtonClick: () -> Unit,
    isButtonEnabled: Boolean,
    inArchive: Boolean,
    modifier: Modifier,
) {
    val density = LocalDensity.current
    val buttonText = if (inArchive) stringResource(R.string.edit_note_remove_from_archive)
    else stringResource(R.string.edit_note_send_to_archive)
    val enabledButtonColor = if (inArchive) {
        ButtonDefaults.buttonColors().containerColor
    } else {
        Color(0xFFB64906)
    }

    Button(
        onClick = { onButtonClick() },
        enabled = isButtonEnabled,
        shape = RoundedCornerShape(5.dp),
        modifier = modifier,
        colors = ButtonColors(
            containerColor = enabledButtonColor,
            disabledContainerColor = ButtonDefaults.buttonColors().disabledContainerColor,
            contentColor = Color.White,
            disabledContentColor = ButtonDefaults.buttonColors().disabledContentColor,
        ),
    ) {
        Text(
            text = buttonText,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.StartEllipsis,
            lineHeight = 12.dp.toSp(density),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ArchiveButtonEnabledButtonPreview() {
    ArchiveButton(
        onButtonClick = {},
        isButtonEnabled = true,
        inArchive = false,
        modifier = Modifier,
    )
}

@Preview(showBackground = true)
@Composable
private fun ArchiveButtonDisabledButtonPreview() {
    ArchiveButton(
        onButtonClick = {},
        isButtonEnabled = false,
        inArchive = false,
        modifier = Modifier,
    )
}

@Preview(showBackground = true)
@Composable
private fun RemoveEnabledButtonPreview() {
    ArchiveButton(
        onButtonClick = {},
        isButtonEnabled = true,
        inArchive = true,
        modifier = Modifier,
    )
}

@Preview(showBackground = true)
@Composable
private fun RemoveDisabledButtonPreview() {
    ArchiveButton(
        onButtonClick = {},
        isButtonEnabled = false,
        inArchive = true,
        modifier = Modifier,
    )
}
