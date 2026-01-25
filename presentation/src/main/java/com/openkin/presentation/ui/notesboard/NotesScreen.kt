package com.openkin.presentation.ui.notesboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.openkin.presentation.R
import com.openkin.presentation.navigation.IAppRouting
import com.openkin.presentation.ui.notesboard.widgets.ActionOnSwipe
import com.openkin.presentation.ui.notesboard.widgets.HorizontalNote
import com.openkin.presentation.ui.notesboard.widgets.SwipeableNote
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
        val notesList by viewModel.notesState.collectAsState()
        var swipedNote by remember { mutableStateOf(Pair(0, -1)) }
        val (
            topBar,
            topGradientDivider,
            notesBoard,
            addNoteButton,
            bottomGradientDivider,
        ) = createRefs()

        //Верхняя панель
        Row (
            modifier = Modifier
                .constrainAs(topBar) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(anchor = parent.start, margin = 16.dp)
                    end.linkTo(anchor = parent.end, margin = 16.dp)
                    height = Dimension.value(56.dp)
                    width = Dimension.fillToConstraints
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.notes_screen_appbar_title),
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier,
            )
            Image(
                painter = painterResource(id = R.drawable.ic_menu_views),
                contentDescription = stringResource(R.string.notes_board_view_button),
                modifier = Modifier
                    .size(36.dp)
                    .padding(start = 8.dp)
            )
        }
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
        ) {
                if (notesList.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier,
                        contentPadding = PaddingValues(bottom = 8.dp)
                    ) {
                        itemsIndexed(items = notesList, key = { _, item -> item.id }) { index, item ->
                            SwipeableNote(
                                isRevealed = item.id == swipedNote.first,
                                actions = {
                                    ActionOnSwipe(
                                        onClick = {
                                            swipedNote = Pair(0, -1)
                                            viewModel.sendNoteToArchive(item.id)
                                        },
                                        drawableId = R.drawable.image_put_to_archive,
                                        contentDescriptionId = R.string.notes_board_replace_to_archive_button,
                                        modifier = Modifier,
                                    )
                                },
                                onExpanded = { swipedNote = Pair(item.id, index) },
                                onCollapsed = { swipedNote = Pair(0, -1) }
                            ) {
                                HorizontalNote(item, routing::addNote)
                            }
                        }
                    }
                } else {
                    Text(text = stringResource(R.string.notes_screen_empty_list))
                }
        }
        //Верхний градиент-разделитель
        val startYellow = Color.White
        val endYellow = Color.Transparent
        Box(
            modifier = Modifier
                .constrainAs(topGradientDivider) {
                    top.linkTo(anchor = topBar.bottom)
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                    height = Dimension.value(16.dp)
                    width = Dimension.fillToConstraints
                }
                .background(brush = Brush.verticalGradient(colors = listOf(startYellow, endYellow))),
        )
        //Нижний градиент-разделитель
        Box(
            modifier = Modifier
                .constrainAs(bottomGradientDivider) {
                    bottom.linkTo(anchor = parent.bottom)
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                    height = Dimension.value(16.dp)
                    width = Dimension.fillToConstraints
                }
                .background(brush = Brush.verticalGradient(colors = listOf(endYellow, startYellow))),
        )
        //Кнопка добавления заметки
        if (notesList.size < 5 || (swipedNote.first == 0 || swipedNote.second != notesList.size-1)) {
            Button(
                shape = CircleShape,
                contentPadding = PaddingValues(8.dp),
                colors = ButtonColors(
                    containerColor = Color(0x07000000),
                    disabledContainerColor = Color.Transparent,
                    contentColor = Color.Transparent,
                    disabledContentColor = Color.Transparent,
                ),
                onClick = { routing.addNote() },
                modifier = Modifier
                    .constrainAs(addNoteButton) {
                        bottom.linkTo(parent.bottom, margin = 32.dp)
                        end.linkTo(anchor = parent.end, margin = 16.dp)
                        height = Dimension.wrapContent
                        width = Dimension.wrapContent
                    },
            ) {
                Image(
                    painter = painterResource(id = R.drawable.image_add_new_note),
                    contentDescription = stringResource(R.string.notes_board_add_note_button),
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.CenterVertically),
                )
            }
        }
    }
}
