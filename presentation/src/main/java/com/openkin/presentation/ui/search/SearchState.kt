package com.openkin.presentation.ui.search

import com.openkin.domain.model.NoteUi

data class SearchState(
    val searchResult: List<NoteUi>,
    val isFilterByTitle: Boolean,
    val searchInProgress: Boolean,
)
