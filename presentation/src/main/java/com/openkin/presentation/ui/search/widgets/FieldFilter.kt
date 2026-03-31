package com.openkin.presentation.ui.search.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openkin.domain.utils.SINGLE_LINE
import com.openkin.presentation.R
import com.openkin.presentation.ui.theme.filterText

@Composable
fun FieldFilter(
    modifier: Modifier,
    onFilterChanged: (Boolean) -> Unit
) {
    var searchByTitle by remember { mutableStateOf(true) }

    Row(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.search_screen_field_filter_label),
            style = MaterialTheme.typography.filterText,
            maxLines = SINGLE_LINE,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(end = 16.dp),
        )
        Column(
            modifier = Modifier.padding(end = 16.dp).weight(1F)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = searchByTitle,
                    onClick = {
                        searchByTitle = true
                        onFilterChanged(true)
                    },
                    modifier = Modifier.size(20.dp),
                )
                Text(
                    text = stringResource(R.string.search_screen_field_filter_type_title),
                    style = MaterialTheme.typography.filterText,
                    maxLines = SINGLE_LINE,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 8.dp, end = 16.dp),
                )
            }
            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = !searchByTitle,
                    onClick = {
                        searchByTitle = false
                        onFilterChanged(false)
                    },
                    modifier = Modifier.size(20.dp),
                )
                Text(
                    text = stringResource(R.string.search_screen_field_filter_type_text),
                    style = MaterialTheme.typography.filterText,
                    maxLines = SINGLE_LINE,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 8.dp),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FilterByFieldPreview() {
    FieldFilter(Modifier) {}
}
