package com.openkin.presentation.ui.search

import com.openkin.domain.model.NoteUi

sealed class SearchState {

    data object SearchInProgress : SearchState()

    data class SearchComplete(val searchResult: List<NoteUi>) : SearchState()
}
