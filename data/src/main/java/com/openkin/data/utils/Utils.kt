package com.openkin.data.utils

import android.util.Log
import java.time.LocalDate

/**
 * Функция оборачивает строку [query], переданную в параметр символами '%'
 * для того чтобы Room мог искать подстроку в указанном столбце.
 * До обёртки все символы '%' экранируются символом '@'.
 */
fun updateQueryForSearchSubstring(query: String) : String {
    val updatedQuery = query.replace("%", "@%")
    return "%$updatedQuery%"
}



fun isReminderInPast(date: LocalDate, time: String): Boolean {
    val now = LocalDate.now()
    var result = false
    Log.d("MyFilter", "date: $date; time: $time - now: $now")
    if (date < now) {
        result = true
    } else if (date == now) {
        val (hour, minute) = time.split(':').map { it.toInt() }
        val calendar = java.util.Calendar.getInstance()
        val currentHour = calendar.get(java.util.Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(java.util.Calendar.MINUTE)
        Log.d("MyFilter", "hour: $hour; minute: $minute currentHour: $currentHour currentMinute: $currentMinute")
        result = hour < currentHour || (hour == currentHour && minute < currentMinute)
    }
    return result
}
