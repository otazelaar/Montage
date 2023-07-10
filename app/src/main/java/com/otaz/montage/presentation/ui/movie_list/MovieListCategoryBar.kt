package com.otaz.montage.presentation.ui.movie_list

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.otaz.montage.presentation.components.movie_list.MovieCategoryChip

@Composable
fun MovieListCategoryBar(
    selectedCategory: MovieCategory?,
    onSelectedCategoryChanged: (String) -> Unit,
    onChangedCategoryScrollPosition: (Int) -> Unit,
    newCategorySearchEvent: () -> Unit,
){
    val categoryScrollState = rememberLazyListState()

    LazyRow(
        modifier = Modifier
            .padding(start = 8.dp, bottom = 8.dp),
        state = categoryScrollState,
    ) {
        items(getAllMovieCategories()){ it ->
            MovieCategoryChip(
                category = it.value,
                isSelected = selectedCategory == it,
                onSelectedCategoryChanged = {
                    onSelectedCategoryChanged(it)
                    onChangedCategoryScrollPosition(categoryScrollState.firstVisibleItemIndex)
                },
                onExecuteSearch = newCategorySearchEvent,
            )
        }
    }
}