package com.openkin.domain.model

import java.time.LocalDate

data class NotificationModel(
    val id: Int,
    val title: String,
    val text: String,
    val targetDate: LocalDate,
    val time: Pair<Int, Int>,
)
