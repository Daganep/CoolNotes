package com.openkin.presentation.ui.addnote

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalDensity
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
import com.openkin.presentation.ui.theme.addNotePlaceHolder
import com.openkin.presentation.ui.theme.buttonText
import com.openkin.presentation.ui.theme.textFieldText
import com.openkin.presentation.ui.theme.white
import com.openkin.presentation.utils.toSp
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@Composable
fun AddNoteScreen(
    routing: IAppRouting,
    targetDate: LocalDate?,
    scaffoldContentPaddings: PaddingValues,
) {
    AddNoteScreen(
        viewModel = koinViewModel(),
        routing = routing,
        targetDate = targetDate,
        scaffoldContentPaddings = scaffoldContentPaddings,
    )
}

@Composable
fun AddNoteScreen(
    viewModel: AddNoteViewModel,
    routing: IAppRouting,
    targetDate: LocalDate?,
    scaffoldContentPaddings: PaddingValues,
) {
    val state by viewModel.viewState.collectAsState()
    val density = LocalDensity.current
    var openConfirmDialog by remember { mutableStateOf(false) }

    ConstraintLayout(
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
            colorBar,
            saveButton,
            targetDateSelector,
        ) = createRefs()
        AddNoteAppBar(
            topAppBarTitle = stringResource(R.string.add_note_screen_appbar_new_title),
            onBackButtonClick = {
                if (state.noteTitle.isNotEmpty() || state.noteText.isNotEmpty()) {
                    openConfirmDialog = true
                } else {
                    routing.goBack()
                }
            },
            modifier = Modifier
                .constrainAs(topBar) {
                    top.linkTo(anchor = parent.top)
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                    height = Dimension.value(56.dp)
                },
        )
        AddNoteTitleField(
            noteTitle = state.noteTitle,
            isNoteTitleExists = state.isNoteTitleExists,
            onNoteTitleChanged = { newTitle -> viewModel.onUpdateTitle(newTitle) },
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
            value = state.noteText,
            onValueChange = { newText -> viewModel.onUpdateNoteText(newText) },
            label = {
                Text(
                    text = stringResource(R.string.add_note_screen_new_text),
                    style = MaterialTheme.typography.addNotePlaceHolder,
                )
            },
            textStyle = MaterialTheme.typography.textFieldText.copy(textAlign = TextAlign.Justify),
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
            onDateChanged = { newDate -> viewModel.onTargetDateChanged(newDate) },
            onTimeChanged = { newTime -> viewModel.onNotifyTimeChanged(newTime) },
            updateNotifyRequestStatus = viewModel::updateNotifyRequestStatus,
            isNotifyWasRequested = state.isNotifyFirstRequest,
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
            onColorClicked = { color -> viewModel.updateCurrentColor(color) },
            currentColor = state.color.startColor,
            modifier = Modifier
                .constrainAs(colorBar) {
                    top.linkTo(anchor = targetDateSelector.bottom, margin = 8.dp)
                    bottom.linkTo(anchor = saveButton.top, margin = 8.dp)
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                    height = Dimension.preferredWrapContent
                    width = Dimension.fillToConstraints
                    verticalBias = 1F
                },
        )
        Button(
            onClick = {
                viewModel.onSaveNote()
                routing.home()
            },
            enabled = !state.isError &&
                state.noteTitle.isNotEmpty() &&
                state.noteTitle.isNotBlank(),
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
            Text(
                text = stringResource(R.string.add_note_save_button),
                style = MaterialTheme.typography.buttonText,
                maxLines = 1,
                lineHeight = 12.dp.toSp(density),
            )
        }

        if (openConfirmDialog) {
            ConfirmDialog(
                onDismissRequest = { openConfirmDialog = false },
                onConfirmation = {
                    openConfirmDialog = false
                    routing.home()
                },
                confirmButtonText = stringResource(R.string.confirm_button_exit_without_save),
                dismissButtonText = stringResource(R.string.dismiss_button_return),
                dialogText = stringResource(R.string.text_exit_without_save),
                iconId = R.drawable.image_note_to_bin,
            )
        }
        BackHandler {
            if (state.noteTitle.isNotEmpty() || state.noteText.isNotEmpty()) {
                openConfirmDialog = true
            } else {
                routing.goBack()
            }
        }
    }
    LaunchedEffect(key1 = true) {
        targetDate?.let { viewModel.onTargetDateChanged(it) }
        viewModel.getNotifyRequestStatus()
    }
}
