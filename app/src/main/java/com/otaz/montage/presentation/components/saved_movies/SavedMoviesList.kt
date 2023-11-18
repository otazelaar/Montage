package com.otaz.montage.presentation.components.saved_movies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.otaz.montage.domain.model.Movie
import com.otaz.montage.presentation.ui.movie_list.MovieListActions
import com.otaz.montage.presentation.ui.movie_list.MovieListState

@Composable
fun SavedMoviesList(
    state: MovieListState,
    actions: (MovieListActions) -> Unit,
    onNavigateToMovieDetailScreen: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        LazyColumn {
            itemsIndexed(
                items = state.savedMovies
            ) { index, movieItem ->
                if (movieItem.isInWatchlist){
                    SavedMoviesListView(
                        movieItem = movieItem,
                        onNavigateToMovieDetailScreen = onNavigateToMovieDetailScreen,
                        actions = actions,
                    )
                }
            }
        }
    }
}