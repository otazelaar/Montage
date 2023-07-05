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
import com.otaz.montage.domain.model.ImageConfigs
import com.otaz.montage.domain.model.Movie
import com.otaz.montage.presentation.components.ShimmerMovieListCardItem
import com.otaz.montage.presentation.navigation.Screen
import com.otaz.montage.util.MOVIE_PAGINATION_PAGE_SIZE

@Composable
fun MovieList(
    loading: Boolean,
    movies: List<Movie>,
    configurations: ImageConfigs,
    onChangeMovieScrollPosition: (Int) -> Unit,
    onTriggerNextPage: () -> Unit,
    page: Int,
    onNavigateToMovieDetailScreen: (String) -> Unit,
    saveMovie: (Movie) -> Unit,
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        if (loading && movies.isEmpty()) {
            ShimmerMovieListCardItem(
                imageHeight = 250.dp,
                padding = 8.dp
            )
        } else {
            LazyColumn {
                itemsIndexed(
                    items = movies
                ){ index, movie ->
                    onChangeMovieScrollPosition(index)
                    if((index + 1) >= (page * MOVIE_PAGINATION_PAGE_SIZE) && !loading){
                        onTriggerNextPage()
                    }
                    MovieListView(
                        movie = movie,
                        configurations = configurations,
                        onClick = {
                            val route = Screen.MovieDetail.route + "/${movie.id}"
                            onNavigateToMovieDetailScreen(route)
                        },
                        onMovieSaveClick = {
                            saveMovie(movie)
                        }
                    )
                }
            }
        }
    }
}