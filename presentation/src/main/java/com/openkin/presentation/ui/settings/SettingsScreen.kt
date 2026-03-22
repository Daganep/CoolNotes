package com.openkin.presentation.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.openkin.presentation.navigation.IAppRouting
import com.openkin.presentation.ui.settings.widgets.Setting
import com.openkin.presentation.ui.settings.widgets.SettingsTopBar
import com.openkin.presentation.ui.theme.white
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(routing: IAppRouting) {
    SettingsScreen(
        viewModel = koinViewModel(),
        routing = routing,
    )
}

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    routing: IAppRouting,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
    ) {
        val (
            topBar,
            settingsList,
            appVersion,
        ) = createRefs()

        SettingsTopBar(
            modifier = Modifier.constrainAs(topBar) {
                top.linkTo(anchor = parent.top)
                start.linkTo(anchor = parent.start)
                end.linkTo(anchor = parent.end)
                height = Dimension.value(56.dp)
            }
        )
        Column(
            modifier = Modifier.constrainAs(settingsList) {
                top.linkTo(anchor = topBar.bottom, margin = 16.dp)
                start.linkTo(anchor = parent.start, margin = 16.dp)
                end.linkTo(anchor = parent.end, margin = 16.dp)
                bottom.linkTo(anchor = appVersion.top, margin = 16.dp)
                verticalBias = 0F
                horizontalBias = 0F
            },
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Setting(
                settingName = "Тема приложения",
                settingValue = "Системная",
                onSettingClick = {},
                modifier = Modifier,
            )
            Setting(
                settingName = "Размер шрифта",
                settingValue = "5",
                onSettingClick = {},
                modifier = Modifier,
            )
        }
        Text(
            text = "Версия приложения 1.0.0",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.constrainAs(appVersion) {
                start.linkTo(anchor = parent.start)
                end.linkTo(anchor = parent.end)
                bottom.linkTo(anchor = parent.bottom, margin = 16.dp)
            },
        )
    }
}
