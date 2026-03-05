package com.openkin.presentation.ui.editnote

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.openkin.presentation.R
import com.openkin.presentation.navigation.IAppRouting
import com.openkin.presentation.ui.addnote.widgets.AddNoteAppBar
import com.openkin.presentation.ui.addnote.widgets.AddNoteColorBar
import com.openkin.presentation.ui.addnote.widgets.AddNoteTitleField
import com.openkin.presentation.ui.addnote.widgets.TargetDate
import com.openkin.presentation.ui.dialogs.ConfirmDialog
import com.openkin.presentation.ui.editnote.widgets.ArchiveButton
import com.openkin.presentation.ui.editnote.widgets.SaveChangesButton
import com.openkin.presentation.ui.theme.white
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditNoteScreen(routing: IAppRouting, noteId: Int, scaffoldContentPaddings: PaddingValues) {
    EditNoteScreen(
        viewModel = koinViewModel(),
        routing = routing,
        noteId = noteId,
        scaffoldContentPaddings = scaffoldContentPaddings,
    )
}

@Composable
fun EditNoteScreen(
    viewModel: EditNoteViewModel,
    routing: IAppRouting,
    noteId: Int,
    scaffoldContentPaddings: PaddingValues,
) {

    val state by viewModel.viewState.collectAsState()
    val event by viewModel.editNoteEvent.collectAsState()
    var openConfirmDialog by remember { mutableStateOf(false) }

    ConstraintLayout (
        modifier = Modifier
            .fillMaxSize()
            .consumeWindowInsets(scaffoldContentPaddings)
            .imePadding()
            .background(white)
            .padding(horizontal = 16.dp),
    ) {
        val (
            topBar,
            noteTitleField,
            noteTextField,
            targetDateSelector,
            colorBar,
            bottomButtons
        ) = createRefs()
        AddNoteAppBar(
            topAppBarTitle = stringResource(R.string.edit_note_screen_appbar_exist_title),
            onBackButtonClick = {
                if (state.isNoteWasChanged && !state.isChangeWasSaved) openConfirmDialog = true
                else routing.goBack()
            },
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
            noteTitle = state.noteTitle,
            isNoteTitleExists = false,
            onNoteTitleChanged = { newTitle -> viewModel.onUpdateTitle(newTitle) },
            modifier = Modifier
                .constrainAs(noteTitleField) {
                    top.linkTo(anchor = topBar.bottom, margin = 8.dp)
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                    height = Dimension.wrapContent
                    width = Dimension.fillToConstraints
                },
        )
        OutlinedTextField(
            value = state.noteText,
            onValueChange = { newText -> viewModel.onUpdateNoteText(newText) },
            label = {
                Text(
                    text = stringResource(R.string.edit_note_screen_new_text),
                    style = MaterialTheme.typography.labelMedium,
                )
            },
            textStyle = MaterialTheme.typography.labelMedium.copy(textAlign = TextAlign.Justify),
            modifier = Modifier
                .constrainAs(noteTextField) {
                    top.linkTo(anchor = noteTitleField.bottom, margin = 8.dp)
                    bottom.linkTo(anchor = targetDateSelector.top)
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                },
        )
        TargetDate(
            selectedDate = state.targetDate,
            selectedTime = state.notifyTime,
            isNoteArchived = state.isArchived,
            onDateChanged = { newDate -> viewModel.onTargetDateChanged(newDate) },
            onTimeChanged = { newTime -> viewModel.onNotifyTimeChanged(newTime) },
            modifier = Modifier
                .constrainAs(targetDateSelector) {
                    top.linkTo(anchor = noteTextField.bottom, margin = 8.dp)
                    bottom.linkTo(anchor = colorBar.top)
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                    width = Dimension.fillToConstraints
                },
        )
        AddNoteColorBar(
            onColorClicked = { color -> viewModel.onUpdateCurrentColor(color) },
            currentColor = state.color.startColor,
            modifier = Modifier
                .constrainAs(colorBar) {
                    top.linkTo(anchor = targetDateSelector.bottom, margin = 8.dp)
                    bottom.linkTo(anchor = bottomButtons.top, margin = 8.dp)
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                    height = Dimension.preferredWrapContent
                    width = Dimension.fillToConstraints
                    verticalBias = 1F
                },
        )
        Row(
            modifier = Modifier
                .constrainAs(bottomButtons) {
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                    bottom.linkTo(anchor = parent.bottom, margin = 4.dp)
                    height = Dimension.value(48.dp)
                    width = Dimension.fillToConstraints
                },
        ) {
            SaveChangesButton(
                onSaveClick = { state.currentNote?.let { viewModel.onUpdateNote() } },
                isButtonEnabled = !state.isError
                        && state.isNoteWasChanged
                        && !state.isChangeWasSaved,
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.5F)
                    .padding(end = 2.dp),
            )
            ArchiveButton(
                onButtonClick = { viewModel.onArchiveClicked() },
                isButtonEnabled = state.currentNote != null,
                inArchive = state.isArchived,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 2.dp),
            )
        }

        if (openConfirmDialog) {
            ConfirmDialog(
                onDismissRequest = { openConfirmDialog = false },
                onConfirmation = {
                    openConfirmDialog = false
                    routing.goBack()
                },
                confirmButtonText = stringResource(R.string.confirm_button_exit_without_save),
                dismissButtonText = stringResource(R.string.dismiss_button_return),
                dialogText = stringResource(R.string.text_exit_without_save),
                iconId = R.drawable.image_note_to_bin,
            )
        }
        if (event) {
            Toast.makeText(
                LocalContext.current,
                stringResource(R.string.edit_note_data_was_saved),
                Toast.LENGTH_SHORT
            ).show()
        }
        BackHandler {
            if (state.isNoteWasChanged && !state.isChangeWasSaved) openConfirmDialog = true
            else routing.goBack()
        }
        LaunchedEffect(key1 = state.currentNote != null) {
            viewModel.onLoadNote(noteId)
        }
    }
}
