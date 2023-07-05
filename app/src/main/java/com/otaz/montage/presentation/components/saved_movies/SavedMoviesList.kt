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
import com.otaz.montage.presentation.navigation.Screen

@Composable
fun SavedMoviesList(
    savedMovies: List<Movie>,
    onNavigateToMovieDetailScreen: (String) -> Unit,
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
                        val route = Screen.MovieDetail.route + "/${movie.id}"
                        onNavigateToMovieDetailScreen(route)
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