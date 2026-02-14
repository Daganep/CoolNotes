package com.openkin.presentation.ui.notesboard.widgets

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SwipeableNote(
    isRevealed: Boolean,
    actions: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    onExpanded: () -> Unit = {},
    onCollapsed: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    var contextMenuWidth by remember {
        mutableFloatStateOf(0F)
    }
    val offset = remember {
        Animatable(initialValue = 0F)
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = isRevealed, key2 = contextMenuWidth) {
        if (isRevealed) offset.animateTo(-contextMenuWidth)
        else offset.animateTo(0F)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(top = 8.dp),
        contentAlignment = Alignment.CenterEnd,
    ) {
        Row(
            modifier = Modifier.onSizeChanged { contextMenuWidth = it.width.toFloat() },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            actions()
        }
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset(offset.value.roundToInt(), 0) } //расстояние, на которое можно сдвинуть элемент
                .pointerInput(true) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount -> //направление движения пальцев для свайпа (dragAmount < 0 справа налево, dragAmount > 0 слева направо)
                            scope.launch {
                                val newOffset = (offset.value + dragAmount)
                                    .coerceIn(
                                        -contextMenuWidth,
                                        0F,
                                    ) //пределы, в которых можно сделать свайп
                                offset.snapTo(newOffset) //приведет offset к значению newOffset, немедленно (в отличии от animateTo)
                            }
                        },
                        onDragEnd = {
                            when {
                                -offset.value >= contextMenuWidth / 2F -> { //условие, при котором свайп выполнится
                                    scope.launch {
                                        offset.animateTo(-contextMenuWidth)
                                        onExpanded()
                                    }
                                }

                                else -> { //иначе элемент вернётся в прежнее положение
                                    scope.launch {
                                        offset.animateTo(0F)
                                        onCollapsed()
                                    }
                                }
                            }
                        }
                    )
                }
        ) {
            content()
        }
    }
}
