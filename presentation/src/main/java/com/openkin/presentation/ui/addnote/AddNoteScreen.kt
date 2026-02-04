package com.openkin.presentation.ui.addnote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.openkin.domain.utils.EMPTY_STRING
import com.openkin.domain.utils.NOTE_TITLE_MAX_LENGTH
import com.openkin.presentation.R
import com.openkin.presentation.navigation.IAppRouting
import com.openkin.presentation.ui.addnote.widgets.AddNoteAppBar
import com.openkin.presentation.ui.addnote.widgets.AddNoteTitleField
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddNoteScreen(routing: IAppRouting, scaffoldContentPaddings: PaddingValues) {
    AddNoteScreen(
        viewModel = koinViewModel(),
        routing = routing,
        scaffoldContentPaddings = scaffoldContentPaddings,
    )
}

@Composable
fun AddNoteScreen(
    viewModel: AddNoteViewModel,
    routing: IAppRouting,
    scaffoldContentPaddings: PaddingValues,
) {
    val noteTitle by viewModel.newNoteTitle.collectAsState()
    val isNoteTitleExists by viewModel.noteExists.collectAsState()
    var noteText by remember { mutableStateOf(EMPTY_STRING) }
    val hasError = isNoteTitleExists || noteTitle.length > NOTE_TITLE_MAX_LENGTH

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
        AddNoteAppBar(
            topAppBarTitle = stringResource(R.string.add_note_screen_appbar_new_title),
            onBackButtonClick = routing::goBack,
            modifier = Modifier
                .constrainAs(topBar) {
                    top.linkTo(anchor = parent.top)
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                    height = Dimension.value(56.dp)
                    width = Dimension.fillToConstraints
                },
        )
        AddNoteTitleField(
            noteTitle = noteTitle,
            isNoteTitleExists = isNoteTitleExists,
            onNoteTitleChanged = { newTitle -> viewModel.updateTitle(newTitle) },
            modifier = Modifier
                .constrainAs(noteTitleField) {
                    top.linkTo(anchor = topBar.bottom, margin = 4.dp)
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                    height = Dimension.wrapContent
                    width = Dimension.fillToConstraints
                },
        )
        OutlinedTextField(
            value = noteText,
            onValueChange = { noteText = it },
            label = { Text(text = stringResource(R.string.add_note_screen_new_text)) },
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
            onClick = {
                viewModel.saveNote(
                    noteTitle = noteTitle,
                    noteText = noteText,
                )
                routing.home()
            },
            enabled = !hasError,
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
            Text(text = stringResource(R.string.add_note_save_button))
        }
    }
}
