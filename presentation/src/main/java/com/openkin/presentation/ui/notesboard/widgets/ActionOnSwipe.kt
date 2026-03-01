package com.openkin.presentation.ui.notesboard.widgets

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.openkin.presentation.ui.theme.transparent

/**
 * Кнопка под смахиваемым (swipe) элементом
 * @param onClick Дейстивие, выполняемое по нажатию.
 * @param drawableId Идентификатор ресурса изображения кнопки.
 * @param contentDescriptionId Идентификатор ресурса описания кнопки.
 */
@Composable
fun ActionOnSwipe(
    onClick: () -> Unit,
    @DrawableRes drawableId: Int,
    @StringRes contentDescriptionId: Int,
    size: Dp = 42.dp,
    modifier: Modifier,
) {
    Button(
        shape = CircleShape,
        contentPadding = PaddingValues(8.dp),
        colors = ButtonColors(
            containerColor = transparent,
            disabledContainerColor = transparent,
            contentColor = transparent,
            disabledContentColor = transparent,
        ),
        onClick = { onClick() },
        modifier = modifier.padding(start = 4.dp),
    ) {
        Image(
            painter = painterResource(id = drawableId),
            contentDescription = stringResource(contentDescriptionId),
            modifier = Modifier
                .size(size)
                .align(Alignment.CenterVertically),
        )
    }
}
