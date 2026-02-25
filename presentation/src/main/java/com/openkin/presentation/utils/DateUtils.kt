package com.openkin.presentation.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun getDate(format: String, date: Long): String =
    SimpleDateFormat(format, Locale.ROOT).format(Date(date))

fun getDate(format: String, date: LocalDate): String =
    date.format(DateTimeFormatter.ofPattern(format))

fun addZero(time: Int): String = if (time < 10) "0$time" else time.toString()

const val SIMPLE_NOTE_DATE_FORMAT = "dd.MM.yyyy"
const val SIMPLE_NOTE_DATE_FORMAT_WITH_TIME = "dd.MM hh:mm"
const val DETAILS_NOTE_DATE_FORMAT = "dd.MM.yy hh:mm:ss"
