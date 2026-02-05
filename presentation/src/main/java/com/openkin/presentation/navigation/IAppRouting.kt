package com.openkin.presentation.navigation

interface IAppRouting {

    fun home()

    fun addNote()

    fun editNote(noteId: Int)

    fun openCalendar()

    fun openSettings()

    fun openArchive()

    fun openSearch()

    fun openBin()

    fun goBack()
}
