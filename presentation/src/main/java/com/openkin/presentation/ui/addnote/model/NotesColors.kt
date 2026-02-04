package com.openkin.presentation.ui.addnote.model

import androidx.compose.ui.graphics.Color

enum class NotesColors(val startColor: Color, val endColor: Color) {
    Yellow(startColor = Color(0xFFFFE87A), endColor = Color(0xFFFBC800)),
    Red(startColor = Color(0xFFFD8484), endColor = Color(0xFFFF3232)),
    Green(startColor = Color(0xFFDAFFDA), endColor = Color(0xFF91FF91)),
    Blue(startColor = Color(0xFFB5F0FF), endColor = Color(0xFF63EDFF)),
    Pink(startColor = Color(0xFFFFA5F7), endColor = Color(0xFFFF59F7)),
    Gray(startColor = Color(0xFFF6F6F6), endColor = Color(0xFFCCCCCC)),
    Purple(startColor = Color(0xFFD99DF1), endColor = Color(0xFFD65AF8)),
}
