package com.openkin.presentation.ui.notesboard.model

import androidx.annotation.StringRes
import com.openkin.presentation.R

enum class ViewType(@StringRes val viewNameId: Int, val typePosition: Int) {
    COMMON_LIST(R.string.view_type_list, 0),
    BIG_BLOCKS(R.string.view_type_big_blocks, 1),
    DETAILS_LIST(R.string.view_type_details_list, 2),
    BLOCKS(R.string.view_type_blocks, 3),
}
