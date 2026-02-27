package com.openkin.presentation.utils

import com.openkin.domain.utils.EMPTY_STRING
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun getDate(format: String, date: Long): String =
    SimpleDateFormat(format, Locale.ROOT).format(Date(date))

fun getDate(format: String, date: LocalDate): String =
    date.format(DateTimeFormatter.ofPattern(format))

fun getTriggerTime(date: LocalDate, time: Pair<Int, Int>): Long? {
    val dateWithTime = "$date ${time.first}:${time.second}"
    return SimpleDateFormat(DETAILS_NOTE_DATE_FORMAT, Locale.ROOT).parse(dateWithTime)?.time
}

fun getNotifyTime(time: Pair<Int, Int>?): String =
    if (time != null) "${addZero(time.first)}:${addZero(time.second)}"
    else EMPTY_STRING

fun addZero(time: Int): String = if (time < 10) "0$time" else time.toString()

const val SIMPLE_NOTE_DATE_FORMAT = "dd.MM.yyyy"
const val SIMPLE_NOTE_DATE_FORMAT_WITH_TIME = "dd.MM hh:mm"
const val DETAILS_NOTE_DATE_FORMAT = "yyyy-MM-dd hh:mm"
