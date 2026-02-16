package com.openkin.presentation.ui.search

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openkin.domain.interactor.INotesInteractor
import com.openkin.domain.utils.EMPTY_STRING
import com.openkin.domain.utils.SEARCH_FIELD_MIN_LENGTH
import com.openkin.domain.utils.SEARCH_FIELD_TIMEOUT_MS
import com.openkin.domain.utils.STOP_TIMEOUT_MS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchViewModel(
    private val notesInteractor: INotesInteractor,
) : ViewModel() {

    private val _viewState = MutableStateFlow(
        SearchState(
            searchResult = listOf(),
            isFilterByTitle = true,
            searchInProgress = false,
        )
    )
    val viewState: StateFlow<SearchState> = _viewState.asStateFlow()
    val searchTextFieldState = TextFieldState()

    @OptIn(FlowPreview::class)
    private val searchTextState = snapshotFlow { searchTextFieldState.text }
        .debounce(SEARCH_FIELD_TIMEOUT_MS)
        .filter { it.isEmpty() || it.length > SEARCH_FIELD_MIN_LENGTH }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = STOP_TIMEOUT_MS),
            initialValue = EMPTY_STRING,
        )

    fun observeSearchFieldChanges() {
        viewModelScope.launch(Dispatchers.Default) {
            searchTextState.collect { query -> searchNotes(query.toString()) }
        }
    }

    fun onChangeFieldFilter(isFilteredByTitle: Boolean) {
        _viewState.value = _viewState.value.copy(
            isFilterByTitle = isFilteredByTitle,
            searchInProgress = true,
        )
        searchNotes(searchTextState.value.toString())
    }

    private fun searchNotes(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val isFilteredByTitle = _viewState.value.isFilterByTitle

            if (query.isBlank() || query.isEmpty()) {
                _viewState.value = _viewState.value.copy(
                    searchResult = listOf(),
                    searchInProgress = false,
                )
            } else {
                _viewState.value = _viewState.value.copy(searchInProgress = true)
                notesInteractor.searchByText(query, isFilteredByTitle).collect { result ->
                    _viewState.value = _viewState.value.copy(
                        searchResult = result,
                        searchInProgress = false,
                    )
                }
            }
        }
    }
}
