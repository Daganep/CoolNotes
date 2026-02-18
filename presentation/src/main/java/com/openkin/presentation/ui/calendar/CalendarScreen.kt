package com.openkin.presentation.ui.calendar

import androidx.compose.runtime.Composable
import com.openkin.presentation.navigation.IAppRouting
import org.koin.androidx.compose.koinViewModel

@Composable
fun CalendarScreen(routing: IAppRouting) {
    CalendarScreen(
        viewModel = koinViewModel(),
        routing = routing,
    )
}

@Composable
fun CalendarScreen(viewModel: CalendarViewModel, routing: IAppRouting) {

}
