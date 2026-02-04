package com.openkin.presentation.ui.archive

import com.openkin.domain.model.NoteUi
import com.openkin.presentation.ui.notesboard.model.SortType

data class ArchiveState(
    val notesList: List<NoteUi>,
    val sortType: Pair<SortType, Boolean>,
    val prevSortType: SortType,
)
