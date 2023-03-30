package com.otaz.imdbmovieapp.presentation.ui.movie_game

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.otaz.imdbmovieapp.presentation.components.ShimmerMovieCardItem
import com.otaz.imdbmovieapp.presentation.components.ShimmerMovieListCardItem
import com.otaz.imdbmovieapp.presentation.components.movie_game.MovieGameView
import com.otaz.imdbmovieapp.presentation.components.util.IMAGE_HEIGHT
import com.otaz.imdbmovieapp.presentation.theme.AppTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieGameScreen(
    viewModel: MovieGameViewModel,
) {
    // The purpose of the following block is to make sure the movie is only called once.
    // Before, this was being performed in the onCreate function but we are no longer using fragments.
    val scaffoldState = rememberScaffoldState()

    val loading = viewModel.loading.value
    val movie = viewModel.movie.value
//        val configurations = viewModel.configurations.value
    val dialogQueue = viewModel.dialogQueue
    val onLoad = viewModel.onLoad.value

    AppTheme(
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
                if (loading && movie == null) {
                    ShimmerMovieCardItem(imageHeight = IMAGE_HEIGHT.dp)
                } else if (!loading && movie == null && onLoad) {
//                        TODO("Show Invalid Movie")
                } else if (movie != null) {
                    MovieGameView(
                        movie = movie,
                    )
                }
            }
        }
    }
}