package com.openkin.presentation.ui.search

import androidx.compose.foundation.text.input.TextFieldState
import com.openkin.domain.model.NoteUi

sealed class SearchState(val textFieldState: TextFieldState) {

    data class SearchInProgress(
        val searchTextFieldState: TextFieldState,
    ) : SearchState(searchTextFieldState)

    data class SearchComplete(
        val searchResult: List<NoteUi>,
        val searchTextFieldState: TextFieldState,
    ) : SearchState(searchTextFieldState)
}
