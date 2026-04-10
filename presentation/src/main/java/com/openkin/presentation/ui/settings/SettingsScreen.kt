package com.openkin.presentation.ui.settings

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.openkin.presentation.R
import com.openkin.presentation.navigation.IAppRouting
import com.openkin.presentation.ui.settings.model.AppTheme
import com.openkin.presentation.ui.settings.widgets.Setting
import com.openkin.presentation.ui.settings.widgets.SettingsTopBar
import com.openkin.presentation.ui.theme.white
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(routing: IAppRouting, onThemeLightChanged: (Boolean) -> Unit) {
    SettingsScreen(
        viewModel = koinViewModel(),
        routing = routing,
        onThemeLightChanged = onThemeLightChanged,
    )
}

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    routing: IAppRouting,
    onThemeLightChanged: (Boolean) -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
    ) {
        val state by viewModel.viewState.collectAsState()
        val isDarkTheme = isSystemInDarkTheme()
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
            modifier = Modifier
                .constrainAs(settingsList) {
                    top.linkTo(anchor = topBar.bottom, margin = 32.dp)
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                    bottom.linkTo(anchor = appVersion.top, margin = 16.dp)
                    verticalBias = 0F
                    horizontalBias = 0F
                }
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Setting(
                settingName = stringResource(R.string.settings_theme),
                settingValue = state.ordinal,
                options = AppTheme.entries.map { stringResource(it.viewNameId) },
                onSettingClick = { index ->
                    val themeColor = AppTheme.entries[index]
                    Log.d("MyFilter", "theme: $themeColor")
                    viewModel.onThemeChanged(themeColor)
                    val result = when (themeColor) {
                        AppTheme.DARK -> true
                        AppTheme.LIGHT -> false
                        else -> isDarkTheme
                    }
                    onThemeLightChanged(result)
                },
                modifier = Modifier.fillMaxWidth(),
            )
            Setting(
                settingName = "Размер шрифта",
                settingValue = 2,
                options = listOf("Kek", "Pek", "Cheburek"),
                onSettingClick = {},
                modifier = Modifier.fillMaxWidth(),
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
    LaunchedEffect(key1 = true) { viewModel.loadSelectedTheme() }
}
