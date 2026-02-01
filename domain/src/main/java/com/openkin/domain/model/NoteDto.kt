package com.openkin.domain.model

data class NoteDto(
    val id: Int,
    val title: String,
    val description: String,
    val createDateMS: Long,
    val editDateMS: Long,
    val archived: Boolean,
)
