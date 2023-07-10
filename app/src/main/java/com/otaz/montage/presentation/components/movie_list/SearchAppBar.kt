package com.otaz.montage.presentation.components.movie_list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import com.otaz.montage.presentation.ui.movie_list.MovieCategory
import com.otaz.montage.presentation.ui.movie_list.MovieListCategoryBar
import com.otaz.montage.presentation.ui.movie_list.MovieListSearchBar

/**
 * I chose to use state hoisting here to improve testability, unidirectional data flow, and reusability of Composables.
 */

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SearchAppBar(
    expression: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    resetForNextSearch: () -> Unit,
    selectedCategory: MovieCategory?,
    onSelectedCategoryChanged: (String) -> Unit,
    onChangedCategoryScrollPosition: (Int) -> Unit,
    newCategorySearchEvent: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.background,
        elevation = 8.dp,
    ) {
        Column {
            MovieListSearchBar(
                onExecuteSearch = onExecuteSearch,
                onQueryChanged = onQueryChanged,
                expression = expression,
                focusManager = focusManager,
                focusRequester = focusRequester,
                resetForNextSearch = resetForNextSearch,
            )
            MovieListCategoryBar(
                selectedCategory = selectedCategory,
                onSelectedCategoryChanged = onSelectedCategoryChanged,
                onChangedCategoryScrollPosition = onChangedCategoryScrollPosition,
                newCategorySearchEvent = newCategorySearchEvent,
            )
        }
    }
}