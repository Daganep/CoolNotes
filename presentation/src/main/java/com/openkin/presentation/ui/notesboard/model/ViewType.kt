package com.openkin.presentation.ui.notesboard.model

import androidx.annotation.StringRes
import com.openkin.presentation.R

enum class ViewType(@StringRes val viewNameId: Int, val typePosition: Int) {
    CommonList(R.string.view_type_list, 0),
    BigBlocks(R.string.view_type_big_blocks, 1),
    DetailsList(R.string.view_type_details_list, 2),
    Blocks(R.string.view_type_blocks, 3),
}
