package com.openkin.presentation.ui.notesboard.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openkin.presentation.R
import com.openkin.presentation.ui.notesboard.ViewType

@Composable
fun TopAppBar(
    modifier: Modifier,
    onViewTypeClick: (ViewType) -> Unit,
) {
    var isViewsMenuExpanded by remember { mutableStateOf(false) }
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.notes_screen_appbar_title),
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier,
        )
        Box {
            Image(
                painter = painterResource(id = R.drawable.ic_menu_views),
                contentDescription = stringResource(R.string.notes_board_view_button),
                modifier = Modifier
                    .size(36.dp)
                    .padding(start = 8.dp)
                    .clickable(
                        interactionSource = null,
                        indication = null,
                        onClick = { isViewsMenuExpanded = !isViewsMenuExpanded }
                    ),
            )
            DropdownMenu(
                expanded = isViewsMenuExpanded,
                onDismissRequest = { isViewsMenuExpanded = false },
                containerColor = Color.White,
                modifier = Modifier.padding(0.dp),
            ) {
                ViewType.entries.forEach { vt ->
                    DropdownMenuItem(
                        text = {
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = stringResource(vt.viewNameId),
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                )
                            }
                        },
                        onClick = {
                            onViewTypeClick(vt)
                            isViewsMenuExpanded = false
                        },
                        contentPadding = PaddingValues(0.dp),
                    )
                }
            }
        }
    }
}