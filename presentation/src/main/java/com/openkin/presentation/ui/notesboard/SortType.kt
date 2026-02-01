package com.openkin.presentation.ui.notesboard

import androidx.annotation.StringRes
import com.openkin.presentation.R

enum class SortType(@StringRes val viewNameId: Int, val typePosition: Int) {
    CREATE_DATE(R.string.sort_type_create_date, 0),
    EDIT_DATE(R.string.sort_type_edit_date, 1),
    ALPHABET(R.string.sort_type_alphabet, 2),
}