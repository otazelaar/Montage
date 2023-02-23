package com.otaz.imdbmovieapp.presentation.ui.movie

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.otaz.imdbmovieapp.presentation.components.IMAGE_HEIGHT
import com.otaz.imdbmovieapp.presentation.components.MovieView
import com.otaz.imdbmovieapp.presentation.components.ShimmerMovieCardItem
import com.otaz.imdbmovieapp.presentation.theme.AppTheme
import com.otaz.imdbmovieapp.presentation.ui.movie.MovieEvent.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel,
    movieId: Int?,
) {
    val loading = viewModel.loading.value
    val movieTmdb = viewModel.movieTmdb.value
    val movieOmdb = viewModel.movieOmdb.value
    val configurations = viewModel.configurations.value

    if(movieId != null && movieTmdb?.imdb_id != null){
        viewModel.onTriggerEvent(GetTmdbMovieEvent(movieId))
        val id = movieTmdb.imdb_id.toString()
        viewModel.onTriggerEvent(GetOmdbMovieEvent(id))
    }

    val dialogQueue = viewModel.dialogQueue
    val scaffoldState = rememberScaffoldState()

    AppTheme(
        displayProgressBar = loading,
        scaffoldState = scaffoldState,
        dialogQueue = dialogQueue.queue.value,
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            snackbarHost = {
                scaffoldState.snackbarHostState
            }
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                if (loading && movieTmdb == null && movieOmdb == null) {
                    ShimmerMovieCardItem(imageHeight = IMAGE_HEIGHT.dp)
                } else if(movieTmdb != null && movieOmdb != null) {
                    MovieView(
                        configurations = configurations,
                        movieTmdb = movieTmdb,
                        movieOmdb = movieOmdb,
                    )
                }
            }
        }
    }
}