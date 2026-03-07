package com.openkin.presentation.ui.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openkin.domain.interactor.INotesInteractor
import com.openkin.domain.interactor.ISettingsInteractor
import com.openkin.domain.model.DaysList
import com.openkin.domain.utils.SEARCH_FIELD_TIMEOUT_MS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate

class CalendarViewModel(
    private val notesInteractor: INotesInteractor,
    private val settingsInteractor: ISettingsInteractor,
) : ViewModel() {

    private val initialState = CalendarState(
        currentDay = LocalDate.now(),
        listOfDays = listOf(),
        notesList = listOf(),
        notesCount = listOf(),
        loadingInProgress = true,
        selectedMonth = LocalDate.now(),
        selectedDay = LocalDate.now(),
    )
    private val _viewState = MutableStateFlow<CalendarState>(initialState)
    val viewState: StateFlow<CalendarState> = _viewState.asStateFlow()

    fun setCurrentDaysList() {
        val daysList = DaysList()
        val today = daysList.getToday()
        getNotesCountForSelectedMonth(daysList.getDaysList())
        onSelectedDayChanged(today)
        _viewState.value = _viewState.value.copy(
            listOfDays = daysList.getDaysList(),
            currentDay = today,
            loadingInProgress = false,
            selectedMonth = today,
        )
    }

    fun onMonthChanged(selectedDate: LocalDate) {
        val daysList = DaysList(selectedDate)
        getNotesCountForSelectedMonth(daysList.getDaysList())
        _viewState.value = _viewState.value.copy(
            listOfDays = daysList.getDaysList(),
            currentDay = daysList.getToday(),
            loadingInProgress = false,
            selectedMonth = selectedDate,
        )
    }

    fun onSelectedDayChanged(day: LocalDate) {
        searchNotesByDate(day)
        _viewState.value = _viewState.value.copy(
            loadingInProgress = true,
            selectedDay = day,
        )
    }

    fun onLoadStoredSelectedDay() {
        viewModelScope.launch(Dispatchers.IO) {
            settingsInteractor
                .getSelectedDay()
                .first()
                ?.let {
                    val month = LocalDate.of(it.year, it.month, _viewState.value.currentDay.dayOfMonth)
                    onMonthChanged(month)
                    onSelectedDayChanged(it)
                }
        }
    }

    private fun searchNotesByDate(day: LocalDate) {
        viewModelScope.launch(Dispatchers.IO) {
            notesInteractor.getNotesByDay(day).collect { notes ->
                _viewState.value = _viewState.value.copy(
                    notesList = notes,
                    loadingInProgress = false,
                )
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun getNotesCountForSelectedMonth(daysList: List<LocalDate>) {
        viewModelScope.launch(Dispatchers.IO) {
            notesInteractor.getNotesCountForSelectedDate(daysList)
                .debounce(SEARCH_FIELD_TIMEOUT_MS)
                .collect { result ->
                    _viewState.value = _viewState.value.copy(notesCount = result)
                }
        }
    }
}
