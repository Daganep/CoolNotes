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

@Composable
fun GradientDivider(
    startColor: Color,
    endColor: Color,
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
fun GradientDividerPreview() {
    GradientDivider(Color.Red, Color.White, Modifier.width(50.dp))
}