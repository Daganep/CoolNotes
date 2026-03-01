package com.openkin.presentation.ui.addnote.widgets

import android.app.DatePickerDialog
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openkin.presentation.R
import com.openkin.presentation.ui.dialogs.RestrictedTimePickerDialog
import com.openkin.presentation.ui.theme.black
import com.openkin.presentation.utils.SIMPLE_NOTE_DATE_FORMAT
import com.openkin.presentation.utils.addZero
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

@Composable
fun TargetDate(
    selectedDate: LocalDate,
    selectedTime: Pair<Int, Int>?,
    modifier: Modifier,
    onDateChanged: (LocalDate) -> Unit,
    onTimeChanged: (Pair<Int, Int>?) -> Unit,
) {
    val datesBorderWidth = 0.5

    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, selectedYear, selectedMonth, selectedDay ->
            onDateChanged(LocalDate.of(selectedYear, selectedMonth+1, selectedDay))
        },
        selectedDate.year, selectedDate.month.value-1, selectedDate.dayOfMonth,
    )
    val timePickerDialog = RestrictedTimePickerDialog(
        context = LocalContext.current,
        selectedDate = selectedDate,
        selectedHour = selectedTime?.first ?: hour,
        selectedMinute = selectedTime?.second ?: minute,
        onTimeChanged = onTimeChanged,
    )
    val timerText = if (selectedTime == null) {
        stringResource(R.string.add_note_empty_timer)
    } else {
        "${addZero(selectedTime.first)}:${addZero(selectedTime.second)}"
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier
                .weight(1F)
                .clickable { datePickerDialog.show() }
                .border(
                    width = datesBorderWidth.dp,
                    color = black,
                    shape = RoundedCornerShape(5.dp),
                )
                .padding(all = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Image(
                painter = painterResource(R.drawable.image_target_calendar),
                contentDescription = stringResource(R.string.add_note_target_date_icon),
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = DateTimeFormatter.ofPattern(SIMPLE_NOTE_DATE_FORMAT).format(selectedDate),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Box(
            modifier = Modifier.weight(1F),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { timePickerDialog.show() }
                    .border(
                        width = datesBorderWidth.dp,
                        color = black,
                        shape = RoundedCornerShape(5.dp),
                    )
                    .padding(all = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
            ) {
                Image(
                    painter = painterResource(R.drawable.image_timer),
                    contentDescription = stringResource(R.string.add_note_timer_icon),
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(24.dp),
                )
                Text(
                    text = timerText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(end = 16.dp),
                )

            }
        }
        AnimatedVisibility(visible = (selectedTime != null)) {
            Image(
                painter = painterResource(R.drawable.icon_clear_edit_text),
                contentDescription = stringResource(R.string.search_screen_clear_search_field),
                modifier = Modifier
                    .size(20.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {

                            onTimeChanged(null)
                        }
                    ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TargetDateWithTimePreview() {
    TargetDate(LocalDate.now(), Pair(5, 5), Modifier, {}, {})
}

@Preview(showBackground = true)
@Composable
private fun TargetDateWithoutTimePreview() {
    TargetDate(LocalDate.now(), null, Modifier, {}, {})
}
