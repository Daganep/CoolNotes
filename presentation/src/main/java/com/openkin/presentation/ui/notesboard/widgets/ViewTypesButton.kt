package com.openkin.presentation.ui.notesboard.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openkin.presentation.R
import com.openkin.presentation.ui.notesboard.model.ViewType
import com.openkin.presentation.ui.theme.white

@Composable
fun ViewTypesButton(
    modifier: Modifier,
    onViewTypeClick: (ViewType) -> Unit,
) {
    var isViewsMenuExpanded by remember { mutableStateOf(false) }
    Box {
        Image(
            painter = painterResource(id = R.drawable.ic_menu_views),
            contentDescription = stringResource(R.string.notes_board_view_button),
            modifier = modifier
                .padding(start = 24.dp)
                .size(24.dp)
                .clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = { isViewsMenuExpanded = !isViewsMenuExpanded }
                ),
        )
        DropdownMenu(
            expanded = isViewsMenuExpanded,
            onDismissRequest = { isViewsMenuExpanded = false },
            containerColor = white,
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
                                style = MaterialTheme.typography.displayMedium,
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

@Preview(showBackground = true)
@Composable
private fun ViewTypesButtonPreview() {
    ViewTypesButton(Modifier, {})
}
