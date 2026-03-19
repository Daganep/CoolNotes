package com.openkin.presentation.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.openkin.presentation.navigation.IAppRouting
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
            themeSettings,
            fontSizeSettings,
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
    }
}
