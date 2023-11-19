package com.otaz.montage.presentation.components.movie_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.otaz.montage.presentation.components.ShimmerMovieListCardItem
import com.otaz.montage.presentation.navigation.Screen
import com.otaz.montage.presentation.ui.movie_list.MovieListActions
import com.otaz.montage.presentation.ui.movie_list.MovieListState
import com.otaz.montage.util.MOVIE_PAGINATION_PAGE_SIZE

@Composable
fun MovieList(
    onNavigateToMovieDetailScreen: (String) -> Unit,
    actions: (MovieListActions) -> Unit,
    state: MovieListState,
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        if (state.loading.value && state.movie.isEmpty()) {
            ShimmerMovieListCardItem(
                imageHeight = 250.dp,
                padding = 8.dp
            )
        } else {
            LazyColumn {
                itemsIndexed(
                    items = state.movie
                ){ index, movieItem ->
                    actions(MovieListActions.MovieScrollPositionChanged(index))

                    if((index + 1) >= (state.page.value * MOVIE_PAGINATION_PAGE_SIZE) && !state.loading.value){
                        actions(MovieListActions.SearchMoviesNextPage)
                    }

                    MovieListView(
                        movieItem = movieItem,
                        onClick = {
                            val route = Screen.MovieDetail.route + "/${movieItem.id}"
                            onNavigateToMovieDetailScreen(route)
                        },
                        state = state,
                        actions = actions,
                    )
                }
            }
        }
    }
}