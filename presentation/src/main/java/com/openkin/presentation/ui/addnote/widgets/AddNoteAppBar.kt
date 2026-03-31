package com.openkin.presentation.ui.addnote.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openkin.domain.utils.SHORT_TEXT_EXAMPLE
import com.openkin.domain.utils.SINGLE_LINE
import com.openkin.presentation.R
import com.openkin.presentation.ui.theme.screenTitle

@Composable
fun AddNoteAppBar(
    topAppBarTitle: String,
    onBackButtonClick: () -> Unit,
    modifier: Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_go_back_arrow),
            contentDescription = stringResource(R.string.back_button),
            modifier = Modifier
                .size(42.dp)
                .clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = { onBackButtonClick() }
                ),
        )
        Text(
            text = topAppBarTitle,
            style = MaterialTheme.typography.screenTitle,
            maxLines = SINGLE_LINE,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 16.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AddNoteAppBarPreview() {
    AddNoteAppBar(
        topAppBarTitle = SHORT_TEXT_EXAMPLE,
        onBackButtonClick = {},
        modifier = Modifier,
    )
}
