package com.openkin.presentation.ui.notesboard.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openkin.presentation.ui.theme.red
import com.openkin.presentation.ui.theme.transparent
import com.openkin.presentation.ui.theme.white

@Composable
fun TopGradientDivider(modifier: Modifier) {
    DefaultGradientDivider(modifier = modifier)
}

@Composable
fun BottomGradientDivider(modifier: Modifier) {
    DefaultGradientDivider(
        startColor = transparent,
        endColor = white,
        modifier = modifier,
    )
}

@Composable
private fun DefaultGradientDivider(
    startColor: Color = white,
    endColor: Color = transparent,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .height(16.dp)
            .background(brush = Brush.verticalGradient(colors = listOf(startColor, endColor)))
    )
}

@Preview(showBackground = true)
@Composable
private fun DefaultGradientDividerPreview() {
    DefaultGradientDivider(red, white, Modifier.width(50.dp))
}
