package com.openkin.presentation.ui.calendar.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openkin.domain.utils.EMPTY_STRING
import com.openkin.presentation.R
import com.openkin.presentation.ui.theme.expandedList
import com.openkin.presentation.ui.theme.extraLightGray
import com.openkin.presentation.ui.theme.monthSelector
import com.openkin.presentation.ui.theme.white
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun MonthSelector(
    selectedDate: LocalDate,
    modifier: Modifier,
    onMonthSelected: (LocalDate) -> Unit,
) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    val currentDate = LocalDate.now()
    val selectedDateMonth = selectedDate
        .month
        .getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())
    val selectedDateYear = if (selectedDate.year != currentDate.year) {
        selectedDate.year
    } else {
        EMPTY_STRING
    }
    val selectorText = "$selectedDateMonth $selectedDateYear".trim().uppercase()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
            .background(extraLightGray, shape = RoundedCornerShape(5.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        IconButton(
            onClick = { onMonthSelected(selectedDate.minusMonths(1)) },
            modifier = Modifier.size(24.dp),
        ) {
            Image(
                painter = painterResource(R.drawable.image_go_back_arrow),
                contentDescription = stringResource(R.string.calendar_screen_previous_month),
                modifier = Modifier.clip(CircleShape)
            )
        }
        Box(
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Text(
                text = selectorText,
                style = MaterialTheme.typography.monthSelector,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clickable(
                        interactionSource = null,
                        indication = null,
                        onClick = { isMenuExpanded = !isMenuExpanded }
                    ),
            )
            DropdownMenu(
                expanded = isMenuExpanded,
                onDismissRequest = { isMenuExpanded = false },
                containerColor = white,
                modifier = Modifier.padding(0.dp),
            ) {
                Month.entries.forEach { month ->
                    val itemColor = if (month == selectedDate.month) extraLightGray else white

                    // TODO оптимизировать DropdownMenuItem
                    DropdownMenuItem(
                        text = {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null,
                                        onClick = {
                                            val newDate = LocalDate.of(
                                                selectedDate.year,
                                                month,
                                                selectedDate.dayOfMonth,
                                            )
                                            onMonthSelected(newDate)
                                            isMenuExpanded = false
                                        },
                                    ),
                            ) {
                                Text(
                                    text = month.getDisplayName(
                                        TextStyle.FULL_STANDALONE,
                                        Locale.getDefault(),
                                    ),
                                    style = MaterialTheme.typography.expandedList,
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                )
                            }
                        },
                        onClick = {},
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.background(itemColor),
                    )
                }
            }
        }
        IconButton(
            modifier = Modifier.size(24.dp),
            onClick = { onMonthSelected(selectedDate.plusMonths(1)) },
        ) {
            Image(
                painter = painterResource(R.drawable.image_go_back_arrow),
                contentDescription = stringResource(R.string.calendar_screen_next_month),
                modifier = Modifier
                    .clip(CircleShape)
                    .scale(scaleX = -1f, scaleY = 1f),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MonthSelectorPreview() {
    MonthSelector(LocalDate.now(), Modifier) {}
}
