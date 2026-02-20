package com.openkin.presentation.ui.calendar

import com.openkin.domain.model.NoteUi
import java.time.LocalDate

data class CalendarState(
    val currentDay: LocalDate,
    val listOfDays: List<LocalDate>,
    val notesList: List<NoteUi>,
    val notesCount: List<Int>,
    val loadingInProgress: Boolean,
    val selectedDate: LocalDate,
    val selectedDay: LocalDate,
)
