package com.openkin.presentation.utils

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

fun Dp.toSp(density: Density) = with(density) { this@toSp.toSp() }
