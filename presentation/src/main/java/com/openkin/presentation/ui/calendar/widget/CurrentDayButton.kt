package com.openkin.presentation.ui.calendar.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openkin.presentation.R
import com.openkin.presentation.utils.toSp
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CurrentDayButton(
    today: LocalDate,
    modifier: Modifier,
    onClick: () -> Unit,
) {
    val monthName = today.month.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())
    val buttonText = "${today.dayOfMonth} ${monthName.take(3).uppercase()}"
    val density = LocalDensity.current
    Box(
        modifier = modifier
            .width(70.dp)
            .clickable(
                interactionSource = null,
                indication = null,
                onClick = { onClick() }
            ),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_today_background),
            contentDescription = stringResource(R.string.calendar_screen_top_bar_date),
        )
        Text(
            text = buttonText,
            fontSize = 14.dp.toSp(density),
            fontWeight = FontWeight.Bold,
            lineHeight = 12.dp.toSp(density),
            modifier = Modifier.padding(top = 12.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CurrentDayButtonPreview() {
    val today = LocalDate.now()
    CurrentDayButton(
        today = today,
        modifier = Modifier,
        onClick = {},
    )
}
