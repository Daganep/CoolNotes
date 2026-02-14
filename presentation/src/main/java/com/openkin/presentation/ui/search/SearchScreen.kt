package com.openkin.presentation.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.openkin.presentation.navigation.IAppRouting
import com.openkin.presentation.ui.notesboard.widgets.HorizontalSimpleNote
import com.openkin.presentation.ui.search.widgets.SearchTopBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(routing: IAppRouting) {
    SearchScreen(
        viewModel = koinViewModel(),
        routing = routing,
    )
}

@Composable
fun SearchScreen(viewModel: SearchViewModel, routing: IAppRouting) {

    val screenState by viewModel.viewState.collectAsStateWithLifecycle()
    val lazyListState = rememberLazyListState()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchTopBar(textFieldState = screenState.textFieldState, modifier = Modifier)
        when (screenState) {
            is SearchState.SearchInProgress -> {
                CircularProgressIndicator(modifier = Modifier.size(50.dp))
            }
            is SearchState.SearchComplete -> {
                val notesList = (screenState as SearchState.SearchComplete).searchResult
                if (notesList.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 8.dp),
                        state = lazyListState,
                    ) {
                        items(items = notesList, key = { it.id }) { item ->
                            HorizontalSimpleNote(item, routing::editNote)
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        val emptyText = if (screenState.textFieldState.text.isBlank()) "Начните поиск"
                        else "Поиск не дал результатов"
                        Text(text = emptyText)
                    }
                }
            }
        }
    }
}
