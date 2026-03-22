package com.openkin.presentation.ui.dialogs

import android.app.TimePickerDialog
import android.content.Context
import android.widget.TimePicker
import java.time.LocalDate
import java.util.Calendar

class RestrictedTimePickerDialog(
    context: Context,
    selectedDate: LocalDate,
    selectedHour: Int,
    selectedMinute: Int,
    onTimeChanged: (Pair<Int, Int>?) -> Unit,
) : TimePickerDialog(
    context,
    { _, pickedHour, pickedMin -> onTimeChanged(Pair(pickedHour, pickedMin)) },
    selectedHour,
    selectedMinute,
    true,
) {
    private val currentDate = LocalDate.now()
    private val calendar = Calendar.getInstance()
    private val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    private val currentMinute = calendar.get(Calendar.MINUTE)
    private val isDateInFuture = selectedDate > currentDate

    override fun onTimeChanged(view: TimePicker, hourOfDay: Int, minute: Int) {
        super.onTimeChanged(view, hourOfDay, minute)
        if (!isDateInFuture) {
            if (hourOfDay < currentHour || (hourOfDay == currentHour && minute < currentMinute)) {
                updateTime(currentHour, currentMinute)
            }
        }
    }
}
