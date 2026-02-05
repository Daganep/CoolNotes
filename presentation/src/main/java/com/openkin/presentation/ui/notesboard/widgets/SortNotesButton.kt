package com.openkin.presentation.ui.notesboard.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openkin.presentation.R
import com.openkin.presentation.ui.notesboard.model.SortType

@Composable
fun SortNotesButton(
    modifier: Modifier,
    onSortClick: (SortType) -> Unit,
) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    var currentSortType by remember { mutableStateOf(SortType.CREATE_DATE) }
    Box {
        Image(
            painter = painterResource(id = R.drawable.image_sort),
            contentDescription = stringResource(R.string.notes_board_sort_button),
            modifier = modifier
                .padding(start = 24.dp)
                .size(28.dp)
                .clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = { isMenuExpanded = !isMenuExpanded }
                ),
        )
        DropdownMenu(
            expanded = isMenuExpanded,
            onDismissRequest = { isMenuExpanded = false },
            containerColor = Color.White,
            modifier = Modifier.padding(0.dp),
        ) {
            SortType.entries.forEach { sortType ->
                val itemColor = if (sortType == currentSortType) Color(0xFFEBEBEB)
                else Color.White
                DropdownMenuItem(
                    text = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    onClick = {
                                        onSortClick(sortType)
                                        currentSortType = sortType
                                        isMenuExpanded = false
                                    },
                                ),
                        ) {
                            Text(
                                text = stringResource(sortType.viewNameId),
                                modifier = Modifier.padding(horizontal = 8.dp),
                            )
                        }
                    },
                    onClick = {},
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.background(itemColor),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SortNotesButtonPreview() {
    SortNotesButton(modifier = Modifier, onSortClick = {})
}
