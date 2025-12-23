package com.openkin.domain.model

data class NoteDto(
    val id: Int,
    val title: String,
    val description: String,
    val createDate: String,
    val archived: Boolean,
)
