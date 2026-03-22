package com.openkin.presentation.ui.settings.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Setting(
    settingName: String,
    settingValue: String,
    onSettingClick: () -> Unit,
    modifier: Modifier,
) {
    Column(
        modifier = modifier.clickable { onSettingClick() }
    ) {
        Text(
            text = settingName,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier,
        )
        Text(
            text = settingValue,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingPreview() {
    Setting("Тема", "Системная", {}, Modifier)
}
