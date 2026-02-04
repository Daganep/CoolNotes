package com.openkin.presentation.ui.notesboard

import com.openkin.domain.model.NoteUi
import com.openkin.presentation.ui.notesboard.model.SortType
import com.openkin.presentation.ui.notesboard.model.ViewType

data class NotesBoardState(
    val notesList: List<NoteUi>,
    val viewType: ViewType,
    val sortType: Pair<SortType, Boolean>,
    val prevSortType: SortType,
)
