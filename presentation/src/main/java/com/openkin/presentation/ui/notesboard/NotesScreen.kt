package com.openkin.presentation.ui.notesboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.openkin.domain.utils.BIG_BLOCKS_COLUMN_COUNT
import com.openkin.domain.utils.SIMPLE_NOTES_TO_HIDE_ADD_BUTTON
import com.openkin.domain.utils.SMALL_BLOCKS_COLUMN_COUNT
import com.openkin.presentation.R
import com.openkin.presentation.navigation.IAppRouting
import com.openkin.presentation.ui.notesboard.model.SortType
import com.openkin.presentation.ui.notesboard.model.ViewType
import com.openkin.presentation.ui.notesboard.widgets.AddNoteFloatButton
import com.openkin.presentation.ui.notesboard.widgets.GradientDivider
import com.openkin.presentation.ui.notesboard.widgets.NotesBlocks
import com.openkin.presentation.ui.notesboard.widgets.NotesCommonList
import com.openkin.presentation.ui.notesboard.widgets.TopAppBar
import kotlinx.coroutines.launch
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

        val state by viewModel.viewState.collectAsState()
        var swipedNote by remember { mutableStateOf(Pair(0, -1)) }
        val lazyListState = rememberLazyListState()
        val lazyGridState = rememberLazyGridState()
        val coroutineScope = rememberCoroutineScope()

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
            onViewTypeClick = { newViewType -> viewModel.saveViewType(newViewType) },
            onSortClick = { sort ->
                if (sort != state.prevSortType) {
                    viewModel.updateSortType(sort, true)
                } else {
                    viewModel.updateSortType(sort, !state.sortType.second)
                }
                coroutineScope.launch {
                    lazyListState.animateScrollToItem(0)
                    lazyGridState.animateScrollToItem(0)
                }
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
                if (state.notesList.isNotEmpty()) {
                    val sortedList = when(state.sortType.first) {
                        SortType.CREATE_DATE -> {
                            if (state.sortType.second) {
                                state.notesList.sortedByDescending { it.createDateMS }
                            } else {
                                state.notesList.sortedBy { it.createDateMS }
                            }
                        }
                        SortType.EDIT_DATE -> {
                            if (state.sortType.second) {
                                state.notesList.sortedByDescending { it.editDateMS }
                            } else {
                                state.notesList.sortedBy { it.editDateMS }
                            }
                        }
                        SortType.ALPHABET -> {
                            if (state.sortType.second) {
                                state.notesList.sortedBy { it.title.lowercase() }
                            } else {
                                state.notesList.sortedByDescending { it.title.lowercase() }
                            }
                        }
                        SortType.COLOR -> {
                            if (state.sortType.second) {
                                state.notesList.sortedBy { it.color }
                            } else {
                                state.notesList.sortedByDescending { it.color }
                            }
                        }
                    }
                    viewModel.updatePrevSortType(state.sortType.first)
                    when (state.viewType) {
                        ViewType.BigBlocks -> {
                            NotesBlocks(
                                notesList = sortedList,
                                onNoteClick = routing::editNote,
                                columnCount = BIG_BLOCKS_COLUMN_COUNT,
                                gridState = lazyGridState,
                            )
                        }
                        ViewType.CommonList -> {
                            NotesCommonList(
                                notesList = sortedList,
                                swipedNote = swipedNote,
                                onNoteSwiped = { id, index -> swipedNote = Pair(id, index) },
                                onNoteClick = routing::editNote,
                                onArchiveClicked = viewModel::sendNoteToArchive,
                                isDetailedList = false,
                                listState = lazyListState,
                            )
                        }
                        ViewType.DetailsList -> {
                            NotesCommonList(
                                notesList = sortedList,
                                swipedNote = swipedNote,
                                onNoteSwiped = { id, index -> swipedNote = Pair(id, index) },
                                onNoteClick = routing::editNote,
                                onArchiveClicked = viewModel::sendNoteToArchive,
                                isDetailedList = true,
                                listState = lazyListState,
                            )
                        }
                        ViewType.Blocks -> {
                            NotesBlocks(
                                notesList = sortedList,
                                onNoteClick = routing::editNote,
                                columnCount = SMALL_BLOCKS_COLUMN_COUNT,
                                gridState = lazyGridState,
                            )
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
        //показать, если заметок меньше чем SIMPLE_NOTES_TO_HIDE_ADD_BUTTON
        //или если ни одна не свайпнута
        //или если свайпнута ни самая последняя из списка заметок
        val isAddButtonNeedToShow = state.notesList.size < SIMPLE_NOTES_TO_HIDE_ADD_BUTTON
                || (swipedNote.first == 0 || swipedNote.second != state.notesList.size-1)
        if (isAddButtonNeedToShow) {
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

        LaunchedEffect(key1 = state.notesList.isNotEmpty()) {
            viewModel.getNotes()
            viewModel.getStoredViewType()
        }
    }
}
