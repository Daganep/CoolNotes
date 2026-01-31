package com.openkin.presentation.ui.notesboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.openkin.presentation.R
import com.openkin.presentation.navigation.IAppRouting
import com.openkin.presentation.ui.notesboard.widgets.AddNoteFloatButton
import com.openkin.presentation.ui.notesboard.widgets.GradientDivider
import com.openkin.presentation.ui.notesboard.widgets.NotesBlocks
import com.openkin.presentation.ui.notesboard.widgets.NotesCommonList
import com.openkin.presentation.ui.notesboard.widgets.TopAppBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun NotesBoard(routing: IAppRouting) {
    NotesBoard(
        viewModel = koinViewModel(),
        routing = routing,
    )
}

@Composable
fun NotesBoard(viewModel: NotesViewModel, routing: IAppRouting) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(Color.White),
    ) {
        viewModel.getNotes()
        viewModel.getStoredViewType()
        val notesList by viewModel.notesState.collectAsState()
        val viewType by viewModel.viewTypeState.collectAsState()
        var swipedNote by remember { mutableStateOf(Pair(0, -1)) }
        val (
            topBar,
            topGradientDivider,
            notesBoard,
            addNoteButton,
            bottomGradientDivider,
        ) = createRefs()

        //Верхняя панель
        TopAppBar(
            modifier = Modifier
                .constrainAs(topBar) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(anchor = parent.start, margin = 16.dp)
                    end.linkTo(anchor = parent.end, margin = 16.dp)
                    height = Dimension.value(56.dp)
                    width = Dimension.fillToConstraints
                },
            onViewTypeClick = {
                newViewType -> viewModel.saveViewType(newViewType)
            },
        )
        //Список заметок
        Box(
            modifier = Modifier.constrainAs(notesBoard) {
                top.linkTo(topBar.bottom, margin = 8.dp)
                bottom.linkTo(parent.bottom, margin = 8.dp)
                start.linkTo(anchor = parent.start, margin = 16.dp)
                end.linkTo(anchor = parent.end, margin = 16.dp)
                height = Dimension.fillToConstraints
                width = Dimension.fillToConstraints
            },
            contentAlignment = Alignment.Center,
        ) {
                if (notesList.isNotEmpty()) {
                    when (viewType) {
                        ViewType.BigBlocks -> {
                            NotesBlocks(
                                notesList = notesList,
                                onNoteClick = routing::addNote,
                            )
                        }
                        ViewType.CommonList -> {
                            NotesCommonList(
                                notesList = notesList,
                                swipedNote = swipedNote,
                                onNoteSwiped = { id, index -> swipedNote = Pair(id, index) },
                                onNoteClick = routing::addNote,
                                onArchiveClicked = viewModel::sendNoteToArchive,
                            )
                        }
                        else -> {
                            //TODO сделать переключатели для остальных видов
                        }
                    }
                } else {
                    Text(text = stringResource(R.string.notes_screen_empty_list))
                }
        }
        //Верхний градиент-разделитель
        GradientDivider(
            startColor = Color.White,
            endColor = Color.Transparent,
            modifier = Modifier
                .constrainAs(topGradientDivider) {
                    top.linkTo(anchor = topBar.bottom)
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                    width = Dimension.fillToConstraints
                }
        )
        //Нижний градиент-разделитель
        GradientDivider(
            startColor = Color.Transparent,
            endColor = Color.White,
            modifier = Modifier
                .constrainAs(bottomGradientDivider) {
                    bottom.linkTo(anchor = parent.bottom)
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                    width = Dimension.fillToConstraints
                }
        )
        //Кнопка добавления заметки
        if (notesList.size < 5 || (swipedNote.first == 0 || swipedNote.second != notesList.size-1)) {
            AddNoteFloatButton(
                onClick = { routing.addNote() },
                modifier = Modifier
                    .constrainAs(addNoteButton) {
                        bottom.linkTo(parent.bottom, margin = 32.dp)
                        end.linkTo(anchor = parent.end, margin = 16.dp)
                        height = Dimension.wrapContent
                        width = Dimension.wrapContent
                    },
            )
        }
    }
}
