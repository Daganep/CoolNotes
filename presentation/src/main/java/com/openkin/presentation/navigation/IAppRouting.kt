package com.openkin.presentation.navigation

import java.time.LocalDate

interface IAppRouting {

    fun home()

    fun addNote(targetDate: LocalDate? = null)

    fun editNote(noteId: Int)

    fun openCalendar()

    fun openSettings()

    fun openArchive()

    fun openSearch()

    fun goBack()
}
