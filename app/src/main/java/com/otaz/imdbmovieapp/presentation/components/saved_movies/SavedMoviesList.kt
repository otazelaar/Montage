package com.otaz.imdbmovieapp.presentation.components.saved_movies

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.otaz.imdbmovieapp.domain.model.Movie
import com.otaz.imdbmovieapp.interactors.app.DeleteMovie
import com.otaz.imdbmovieapp.presentation.navigation.Screen
import com.otaz.imdbmovieapp.util.MOVIE_PAGINATION_PAGE_SIZE

@Composable
fun SavedMoviesList(
    savedMovies: List<Movie>,
    onNavigateToMovieDetailScreen: (Movie) -> Unit,
    onClickDeleteMovie: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        LazyColumn {
            itemsIndexed(
                items = savedMovies
            ) { index, movie ->
                SavedMoviesListView(
                    movie = movie,
                    onClick = {
                        onNavigateToMovieDetailScreen(movie)
                    },
                    onClickDeleteMovie = {
                        val clickedMovieIDToBeDeleted = it.id.toString()
                        onClickDeleteMovie(clickedMovieIDToBeDeleted)
                    },
                )
            }
        }
    }
}