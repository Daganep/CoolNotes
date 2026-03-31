package com.openkin.presentation.ui.search.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.delete
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openkin.domain.utils.SEARCH_FIELD_MAX_LENGTH
import com.openkin.domain.utils.SINGLE_LINE
import com.openkin.presentation.R
import com.openkin.presentation.ui.theme.lightGray
import com.openkin.presentation.ui.theme.searchFieldText
import com.openkin.presentation.ui.theme.textFieldText

@Composable
fun SearchTopBar(
    textFieldState: TextFieldState,
    modifier: Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Box(
                modifier = Modifier.weight(1F),
                contentAlignment = Alignment.CenterStart,
            ) {
                Row(
                    modifier = Modifier
                        .background(color = lightGray, shape = RoundedCornerShape(10.dp))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Image(
                        painter = painterResource(R.drawable.image_search),
                        contentDescription = stringResource(R.string.search_screen_search_icon),
                        modifier = Modifier.size(20.dp),
                    )
                    BasicTextField(
                        state = textFieldState,
                        modifier = Modifier.weight(1F),
                        lineLimits = TextFieldLineLimits.SingleLine,
                        inputTransformation = InputTransformation.maxLength(SEARCH_FIELD_MAX_LENGTH),
                        textStyle = MaterialTheme.typography.searchFieldText,
                    )
                }
                androidx.compose.animation.AnimatedVisibility(
                    visible = textFieldState.text.isBlank(),
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    Text(
                        text = stringResource(R.string.search_screen_top_bar_placeholder),
                        style = MaterialTheme.typography.textFieldText,
                        maxLines = SINGLE_LINE,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = 48.dp),
                    )
                }
            }
            AnimatedVisibility(visible = textFieldState.text.isNotBlank()) {
                Image(
                    painter = painterResource(R.drawable.icon_clear_edit_text),
                    contentDescription = stringResource(R.string.search_screen_clear_search_field),
                    modifier = Modifier
                        .size(20.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { textFieldState.edit { delete(0, length) } }
                        ),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchTopBarPreview() {
    SearchTopBar(textFieldState = TextFieldState(), modifier = Modifier)
}
