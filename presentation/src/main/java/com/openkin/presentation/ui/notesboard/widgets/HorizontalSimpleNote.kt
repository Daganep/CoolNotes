package com.openkin.presentation.ui.notesboard.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.openkin.domain.model.NoteUi
import com.openkin.domain.utils.ID_EXAMPLE_INT
import com.openkin.domain.utils.ID_EXAMPLE_LONG
import com.openkin.domain.utils.LONG_TEXT_EXAMPLE
import com.openkin.presentation.R
import com.openkin.presentation.ui.addnote.model.NotesColors
import com.openkin.presentation.utils.SIMPLE_NOTE_DATE_FORMAT
import com.openkin.presentation.utils.getDate

@Composable
fun HorizontalSimpleNote(
    note: NoteUi,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val noteColors = NotesColors.entries.first { it.name == note.color }
    val linearGradient = Brush.linearGradient(
        colors = listOf(noteColors.startColor, noteColors.endColor)
    )
    val createDate = getDate(SIMPLE_NOTE_DATE_FORMAT, note.createDateMS)

    Card(
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = null,
                enabled = true,
                onClickLabel = null,
                role = Role.Button,
                onClick =  { onClick(note.id) },
            ),
    ) {
        ConstraintLayout (
            modifier = Modifier
                .fillMaxSize()
                .background(brush = linearGradient)
        ) {
            val (title, description, date) = createRefs()
            Text(
                text = note.title,
                fontFamily = FontFamily(Font(R.font.calibri_bold)),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
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
                text = note.description,
                fontFamily = FontFamily(Font(R.font.calibri)),
                fontSize = 14.sp,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 14.sp,
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
            Row(
                modifier = Modifier
                .constrainAs(date) {
                    bottom.linkTo(anchor = parent.bottom, margin = 2.dp)
                    start.linkTo(anchor = parent.start, margin = 8.dp)
                },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(R.drawable.image_target_calendar),
                    contentDescription = "",
                    modifier = Modifier.padding(end = 8.dp).size(12.dp)
                )
                Text(
                    text = createDate,
                    fontSize = 9.sp,
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun HorizontalNotePreview() {
    HorizontalSimpleNote(
        note = NoteUi(
            id = ID_EXAMPLE_INT,
            title = LONG_TEXT_EXAMPLE,
            description = LONG_TEXT_EXAMPLE,
            createDateMS = ID_EXAMPLE_LONG,
        ),
        onClick = {},
    )
}
