package com.openkin.presentation.ui.notesboard.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.openkin.domain.model.NoteUi
import com.openkin.domain.utils.ID_EXAMPLE_INT
import com.openkin.domain.utils.ID_EXAMPLE_LONG
import com.openkin.domain.utils.LONG_TEXT_EXAMPLE
import com.openkin.presentation.ui.addnote.model.NotesColors
import com.openkin.presentation.utils.SIMPLE_NOTE_DATE_FORMAT
import com.openkin.presentation.utils.getDate

@Composable
fun SmallSquareNote(
    note: NoteUi,
    onNoteClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val noteColors = NotesColors.entries.first { it.name == note.color }
    val linearGradient = Brush.linearGradient(
        colors = listOf(noteColors.startColor, noteColors.endColor)
    )
    val targetDate = getDate(SIMPLE_NOTE_DATE_FORMAT, note.targetDate)

    Box {
        Card(
            shape = RoundedCornerShape(0.dp),
            elevation = CardDefaults.cardElevation(5.dp),
            modifier = modifier
                .height(110.dp)
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = null,
                    enabled = true,
                    onClickLabel = null,
                    role = Role.Button,
                    onClick =  { onNoteClick(note.id) },
                ),
        ) {
            ConstraintLayout (
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = linearGradient),
            ) {
                val (title, description, date) = createRefs()
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .constrainAs(title) {
                            top.linkTo(anchor = parent.top, margin = 8.dp)
                            start.linkTo(anchor = parent.start, margin = 8.dp)
                            end.linkTo(anchor = parent.end, margin = 8.dp)
                            width = Dimension.fillToConstraints
                        },
                )
                Text(
                    text = note.text,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .constrainAs(description) {
                            top.linkTo(anchor = title.bottom, margin = 4.dp)
                            bottom.linkTo(anchor = date.top, margin = 2.dp)
                            start.linkTo(anchor = parent.start, margin = 8.dp)
                            end.linkTo(anchor = parent.end, margin = 8.dp)
                            width = Dimension.fillToConstraints
                            height = Dimension.preferredWrapContent
                            verticalBias = 0F
                        },
                )
                Text(
                    text = targetDate,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .constrainAs(date) {
                            bottom.linkTo(anchor = parent.bottom)
                            start.linkTo(anchor = parent.start, margin = 8.dp)
                        },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SmallSquareNotePreview() {
    SmallSquareNote(
        note = NoteUi(
            id = ID_EXAMPLE_INT,
            title = LONG_TEXT_EXAMPLE,
            text = LONG_TEXT_EXAMPLE,
            createDateMS = ID_EXAMPLE_LONG,
        ),
        onNoteClick = {},
    )
}
