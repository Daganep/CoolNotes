package com.openkin.domain.model

import java.time.LocalDate

data class NoteDto(
    val id: Int,
    val title: String,
    val description: String,
    val color: String,
    val createDateMS: Long,
    val editDateMS: Long,
    val archived: Boolean,
    val targetDate: LocalDate,
)
