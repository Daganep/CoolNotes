package com.openkin.presentation.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getDate(format: String, date: Long): String =
    SimpleDateFormat(format, Locale.ROOT).format(Date(date))

const val SIMPLE_NOTE_DATE_FORMAT = "dd.MM hh:mm"
const val DETAILS_NOTE_DATE_FORMAT = "dd.MM.yy hh:mm:ss"
