package com.openkin.presentation.ui.addnote.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ColorBox(
    color: Color,
    isColorPicked: Boolean,
    onBoxClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val borderWidth = if (isColorPicked) 3.dp else 1.dp
    val borderColor = if (isColorPicked) Color(0xFF0785C7) else Color.Black
    val boxSize = if (isColorPicked) 35.dp else 30.dp

    Box(
        modifier = modifier
            .border(width = borderWidth, color = borderColor, shape = RoundedCornerShape(5.dp))
            .size(boxSize)
            .clickable(
                interactionSource = null,
                indication = null,
                onClick = { onBoxClicked() }
            ),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .padding(1.dp)
                .background(color)
                .size(20.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PickedColorBoxPreview() {
    ColorBox(color = Color.Red, isColorPicked = true, onBoxClicked = {}, modifier = Modifier)
}

@Preview(showBackground = true)
@Composable
fun ColorBoxPreview() {
    ColorBox(color = Color.Red, isColorPicked = false, onBoxClicked = {}, modifier = Modifier)
}
