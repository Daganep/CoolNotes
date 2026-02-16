package com.openkin.presentation.ui.search

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openkin.domain.interactor.INotesInteractor
import com.openkin.domain.utils.EMPTY_STRING
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

    private val _viewState = MutableStateFlow<SearchState>(SearchState.SearchComplete(listOf()))
    val viewState: StateFlow<SearchState> = _viewState.asStateFlow()
    val searchTextFieldState = TextFieldState()

    @OptIn(FlowPreview::class)
    private val searchTextState = snapshotFlow { searchTextFieldState.text }
        .debounce(500)
        .filter { it.isEmpty() || it.length > 2 }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 2000),
            initialValue = EMPTY_STRING,
        )

    fun observeSearchFieldChanges() {
        viewModelScope.launch(Dispatchers.IO) {
            searchTextState.collect { query ->
                if (query.isBlank() || query.isEmpty()) {
                    _viewState.value = SearchState.SearchComplete(searchResult = listOf())
                } else {
                    _viewState.value = SearchState.SearchInProgress
                    searchNotes(query.toString())
                }
            }
        }
    }

    private fun searchNotes(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            notesInteractor.searchByTitle(query).collect { result ->
                _viewState.value = SearchState.SearchComplete(searchResult = result)
            }
        }
    }
}
