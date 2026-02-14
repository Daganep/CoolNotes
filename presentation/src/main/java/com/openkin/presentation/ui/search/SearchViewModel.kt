package com.openkin.presentation.ui.search

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openkin.domain.utils.EMPTY_STRING
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchViewModel(

) : ViewModel() {

    private val searchTextFieldState = TextFieldState()

    @OptIn(FlowPreview::class)
    private val searchTextState = snapshotFlow { searchTextFieldState.text }
        .debounce(500)
        .filterNot { it.isBlank() || it.isEmpty() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 2000),
            initialValue = EMPTY_STRING,
        )

    private val defaultState = SearchState.SearchComplete(
        searchResult = listOf(),
        searchTextFieldState = searchTextFieldState,
    )
    private val _viewState = MutableStateFlow<SearchState>(defaultState)
    val viewState: StateFlow<SearchState> = _viewState.asStateFlow()

    init {
        observeSearchFieldChanges()
    }

    private fun observeSearchFieldChanges() {
        viewModelScope.launch(Dispatchers.IO) {
            searchTextState.collect { query ->
                _viewState.value = SearchState.SearchInProgress(searchTextFieldState)
                searchNotes(query.toString())
            }
        }
    }

    private fun searchNotes(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.value = SearchState.SearchComplete(
                searchResult = listOf(),
                searchTextFieldState = searchTextFieldState,
            )
        }
    }
}
