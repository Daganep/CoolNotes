package com.openkin.presentation.ui.settings.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openkin.presentation.R
import com.openkin.presentation.ui.dialogs.SettingsDialog
import com.openkin.presentation.ui.theme.settingName
import com.openkin.presentation.ui.theme.settingValue

@Composable
fun Setting(
    settingName: String,
    settingValue: Int,
    options: List<String>,
    onSettingClick: (Int) -> Unit,
    modifier: Modifier,
) {
    var showDialog by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(5.dp))
            .clickable { showDialog = true }
            .padding(8.dp)
    ) {
        Text(
            text = settingName,
            style = MaterialTheme.typography.settingName,
            modifier = Modifier,
        )
        Text(
            text = options[settingValue],
            style = MaterialTheme.typography.settingValue,
            modifier = Modifier.padding(top = 8.dp),
        )
    }
    if (showDialog) {
        SettingsDialog(
            options = options,
            selectedOption = settingValue,
            onDismissRequest = { showDialog = false },
            onConfirmation = {
                onSettingClick(it)
                showDialog = false
            },
            confirmButtonText = stringResource(R.string.confirm_button_select_option),
            dismissButtonText = stringResource(R.string.dismiss_button_cancel),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingPreview() {
    Setting("Тема приложения", 1, listOf(), {}, Modifier)
}
