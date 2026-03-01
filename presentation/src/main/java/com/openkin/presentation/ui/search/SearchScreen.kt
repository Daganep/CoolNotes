package com.openkin.presentation.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.openkin.presentation.R
import com.openkin.presentation.navigation.IAppRouting
import com.openkin.presentation.ui.notesboard.widgets.BottomGradientDivider
import com.openkin.presentation.ui.notesboard.widgets.HorizontalSimpleNote
import com.openkin.presentation.ui.notesboard.widgets.TopGradientDivider
import com.openkin.presentation.ui.search.widgets.FieldFilter
import com.openkin.presentation.ui.search.widgets.SearchTopBar
import com.openkin.presentation.ui.theme.white
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(routing: IAppRouting, scaffoldContentPaddings: PaddingValues) {
    SearchScreen(
        viewModel = koinViewModel(),
        routing = routing,
        scaffoldContentPaddings = scaffoldContentPaddings,
    )
}

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    routing: IAppRouting,
    scaffoldContentPaddings: PaddingValues,
) {

    val screenState by viewModel.viewState.collectAsStateWithLifecycle()
    val lazyListState = rememberLazyListState()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .consumeWindowInsets(scaffoldContentPaddings)
            .imePadding()
            .background(white),
    ) {
        val (
            topBar,
            fieldFilter,
            topGradientDivider,
            bottomGradientDivider,
            resultList,
        ) = createRefs()

        SearchTopBar(
            textFieldState = viewModel.searchTextFieldState,
            modifier = Modifier
                .constrainAs(topBar) {
                    top.linkTo(anchor = parent.top)
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                }
        )

        FieldFilter(
            onFilterChanged = { isFilteredByTitle ->
                viewModel.onChangeFieldFilter(isFilteredByTitle)
            },
            modifier = Modifier
                .constrainAs(fieldFilter) {
                    top.linkTo(anchor = topBar.bottom)
                    start.linkTo(anchor = parent.start, margin = 16.dp)
                    end.linkTo(anchor = parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints

                }
        )

        //Результаты поиска
        Box(
            modifier = Modifier
                .constrainAs(resultList) {
                    top.linkTo(anchor = fieldFilter.bottom)
                    bottom.linkTo(anchor = parent.bottom)
                    start.linkTo(anchor = parent.start, margin = 16.dp)
                    end.linkTo(anchor = parent.end, margin = 16.dp)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                },
            contentAlignment = Alignment.Center,
        ) {
            if (!screenState.searchInProgress) {
                val notesList = screenState.searchResult
                if (notesList.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 8.dp),
                        state = lazyListState,
                    ) {
                        items(items = notesList, key = { it.id }) { item ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                contentAlignment = Alignment.CenterEnd,
                            ) {
                                HorizontalSimpleNote(item, routing::editNote)
                            }
                        }
                    }
                } else {
                    val emptyText = if (viewModel.searchTextFieldState.text.isBlank()) {
                        stringResource(R.string.search_screen_start_search)
                    } else stringResource(R.string.search_screen_empty_result)
                    Text(text = emptyText)
                }
            } else {
                CircularProgressIndicator(modifier = Modifier.size(50.dp))
            }
        }

        //Верхний градиент-разделитель
        TopGradientDivider(
            modifier = Modifier
                .constrainAs(topGradientDivider) {
                    top.linkTo(anchor = fieldFilter.bottom)
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

    LaunchedEffect(true) {
        viewModel.observeSearchFieldChanges()
    }
}
