package com.openkin.presentation.ui.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openkin.domain.interactor.INotesInteractor
import com.openkin.domain.utils.SEARCH_FIELD_TIMEOUT_MS
import com.openkin.domain.model.DaysList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneOffset

class CalendarViewModel(
    private val notesInteractor: INotesInteractor,
) : ViewModel() {

    private val initialState = CalendarState(
        currentDay = LocalDate.now(),
        listOfDays = listOf(),
        notesList = listOf(),
        notesCount = listOf(),
        loadingInProgress = true,
        selectedDate = LocalDate.now(),
        selectedDay = LocalDate.now(),
    )
    private val _viewState = MutableStateFlow<CalendarState>(initialState)
    val viewState: StateFlow<CalendarState> = _viewState.asStateFlow()

    fun setCurrentDaysList() {
        val daysList = DaysList()
        val today = daysList.getToday()
        getNotesCountForSelectedDate(daysList.getDaysList())
        onSelectedDayChanged(today)
        _viewState.value = _viewState.value.copy(
            listOfDays = daysList.getDaysList(),
            currentDay = today,
            loadingInProgress = false,
            selectedDate = today,
        )
    }

    fun onMonthChanged(selectedDate: LocalDate) {
        val daysList = DaysList(selectedDate)
        getNotesCountForSelectedDate(daysList.getDaysList())
        _viewState.value = _viewState.value.copy(
            listOfDays = daysList.getDaysList(),
            currentDay = daysList.getToday(),
            loadingInProgress = false,
            selectedDate = selectedDate,
        )
    }

    fun onSelectedDayChanged(day: LocalDate) {
        val startOfDay = day.atStartOfDay(ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli()
        val endOfDay = day.plusDays(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli()
        searchNotesByDate(Pair(startOfDay, endOfDay))
        _viewState.value = _viewState.value.copy(
            loadingInProgress = true,
            selectedDay = day,
        )
    }

    private fun searchNotesByDate(period: Pair<Long, Long>) {
        viewModelScope.launch(Dispatchers.IO) {
            notesInteractor.getNotesByDateRange(period.first, period.second).collect { notes ->
                _viewState.value = _viewState.value.copy(
                    notesList = notes,
                    loadingInProgress = false,
                )
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun getNotesCountForSelectedDate(daysList: List<LocalDate>) {
        viewModelScope.launch(Dispatchers.IO) {
            notesInteractor.getNotesCountForSelectedDate(daysList)
                .debounce(SEARCH_FIELD_TIMEOUT_MS)
                .collect { result ->
                    _viewState.value = _viewState.value.copy(notesCount = result)
                }
        }
    }
}
