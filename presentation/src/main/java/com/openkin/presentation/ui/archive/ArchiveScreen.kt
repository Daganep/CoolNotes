package com.openkin.presentation.ui.archive

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.openkin.presentation.R
import com.openkin.presentation.navigation.IAppRouting
import com.openkin.presentation.ui.archive.widgets.ArchiveTopBar
import com.openkin.presentation.ui.notesboard.model.SortType
import com.openkin.presentation.ui.notesboard.widgets.ActionOnSwipe
import com.openkin.presentation.ui.notesboard.widgets.BottomGradientDivider
import com.openkin.presentation.ui.notesboard.widgets.HorizontalSimpleNote
import com.openkin.presentation.ui.notesboard.widgets.SwipeableNote
import com.openkin.presentation.ui.notesboard.widgets.TopGradientDivider
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun ArchiveScreen(routing: IAppRouting) {
    ArchiveScreen(
        viewModel = koinViewModel(),
        routing = routing,
    )
}

@Composable
fun ArchiveScreen(viewModel: ArchiveViewModel, routing: IAppRouting) {

    val state by viewModel.viewState.collectAsState()
    val loadingState by viewModel.loadingState.collectAsState()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {

        val (
            topBar,
            topGradientDivider,
            bottomGradientDivider,
            notesList,
        ) = createRefs()

        val lazyListState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        ArchiveTopBar(
            onSortClick = { sort ->
                if (sort != state.prevSortType) {
                    viewModel.updateSortType(sort, true)
                } else {
                    viewModel.updateSortType(sort, !state.sortType.second)
                }
                coroutineScope.launch { lazyListState.animateScrollToItem(0) }
            },
            modifier = Modifier
                .constrainAs(topBar) {
                    top.linkTo(anchor = parent.top)
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                    height = Dimension.value(56.dp)
                }
        )

        //Список заметок
        Box(
            modifier = Modifier.constrainAs(notesList) {
                top.linkTo(topBar.bottom, margin = 8.dp)
                bottom.linkTo(parent.bottom, margin = 8.dp)
                start.linkTo(anchor = parent.start, margin = 16.dp)
                end.linkTo(anchor = parent.end, margin = 16.dp)
                height = Dimension.fillToConstraints
                width = Dimension.fillToConstraints
            },
            contentAlignment = Alignment.Center,
        ) {
            if (loadingState) {
                CircularProgressIndicator(modifier = Modifier.size(50.dp))
            } else if (state.notesList.isEmpty()) {
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(R.string.archive_screen_empty_list),
                        fontSize = 18.sp,
                    )
                }
            } else {
                var swipedNote by remember { mutableIntStateOf(0) }
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

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 8.dp),
                ) {
                    items(items = sortedList, key = { it.id }) { item ->
                        SwipeableNote(
                            isRevealed = item.id == swipedNote,
                            actions = {
                                ActionOnSwipe(
                                    onClick = {
                                        swipedNote = 0
                                        viewModel.returnNoteToBoard(item.id)
                                    },
                                    drawableId = R.drawable.image_back_to_board_arrow,
                                    contentDescriptionId = R.string.archive_screen_delete_archive_button,
                                    size = 52.dp,
                                    modifier = Modifier,
                                )
                                ActionOnSwipe(
                                    onClick = {
                                        swipedNote = 0
                                        viewModel.removeNote(item.id)
                                    },
                                    drawableId = R.drawable.image_note_to_bin,
                                    contentDescriptionId = R.string.archive_screen_delete_archive_button,
                                    modifier = Modifier,
                                )
                            },
                            onExpanded = { swipedNote = item.id },
                            onCollapsed = { swipedNote = 0 }
                        ) {
                            HorizontalSimpleNote(item, routing::editNote)
                        }
                    }
                }
            }
        }

        //Верхний градиент-разделитель
        TopGradientDivider(
            modifier = Modifier
                .constrainAs(topGradientDivider) {
                    top.linkTo(anchor = topBar.bottom)
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                    width = Dimension.fillToConstraints
                }
        )

        //Нижний градиент-разделитель
        BottomGradientDivider(
            modifier = Modifier
                .constrainAs(bottomGradientDivider) {
                    bottom.linkTo(anchor = parent.bottom)
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                    width = Dimension.fillToConstraints
                }
        )
    }

    LaunchedEffect(key1 = !loadingState) {
        viewModel.getArchive()
    }
}
