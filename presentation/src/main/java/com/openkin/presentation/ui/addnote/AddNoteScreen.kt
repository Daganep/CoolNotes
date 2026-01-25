package com.openkin.presentation.ui.addnote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.openkin.domain.model.NoteUi
import com.openkin.presentation.R
import com.openkin.presentation.navigation.IAppRouting
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddNoteScreen(routing: IAppRouting, noteId: Int?, scaffoldContentPaddings: PaddingValues) {
    AddNoteScreen(
        viewModel = koinViewModel(),
        routing = routing,
        noteId = noteId,
        scaffoldContentPaddings = scaffoldContentPaddings,
    )
}

@Composable
fun AddNoteScreen(
    viewModel: AddNoteViewModel,
    routing: IAppRouting,
    noteId: Int?,
    scaffoldContentPaddings: PaddingValues,
) {
    noteId?.let { viewModel.getNote(it) }
    val archivedNotes by remember { mutableStateOf<List<NoteUi>>(listOf()) }
    var noteTitle by remember { mutableStateOf<String>("") }
    var noteText by remember { mutableStateOf<String>("") }
    val currentNote by viewModel.currentNote.collectAsState()
    currentNote?.let {
        noteTitle = it.title
        noteText = it.description
    }
    ConstraintLayout (
        modifier = Modifier
            .fillMaxSize()
            .consumeWindowInsets(scaffoldContentPaddings)
            .imePadding()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(Color.White)
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
    ) {
        val (topBar, noteTitleField, noteTextField, saveButton) = createRefs()
        Row(modifier = Modifier
            .constrainAs(topBar) {
                top.linkTo(anchor = parent.top)
                start.linkTo(anchor = parent.start)
                end.linkTo(anchor = parent.end)
                height = Dimension.value(56.dp)
                width = Dimension.fillToConstraints
            },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.add_note_screen_appbar_title),
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier,
            )
        }
        OutlinedTextField(
            value = noteTitle,
            onValueChange = { noteTitle = it },
            label = { Text(text = "Название заметки") },
            singleLine = true,
            modifier = Modifier
                .constrainAs(noteTitleField) {
                    top.linkTo(anchor = topBar.bottom)
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                    height = Dimension.wrapContent
                    width = Dimension.fillToConstraints
                },
        )
        OutlinedTextField(
            value = noteText,
            onValueChange = { noteText = it },
            label = { Text(text = "Текст заметки") },
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Justify),
            modifier = Modifier
                .constrainAs(noteTextField) {
                    top.linkTo(anchor = noteTitleField.bottom, margin = 4.dp)
                    bottom.linkTo(anchor = saveButton.top, margin = 8.dp)
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                },
        )
        Button(
            onClick = { viewModel.saveNote(noteTitle = noteTitle, noteText = noteText) },
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .constrainAs(saveButton) {
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                    bottom.linkTo(anchor = parent.bottom, margin = 4.dp)
                    height = Dimension.value(48.dp)
                    width = Dimension.fillToConstraints
                },
        ) {
            Text(text = "Сохранить")
        }
    }
}
