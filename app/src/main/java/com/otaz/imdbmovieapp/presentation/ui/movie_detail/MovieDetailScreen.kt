package com.otaz.imdbmovieapp.presentation.ui.movie_detail

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
import com.otaz.imdbmovieapp.presentation.components.movie_detail.MovieDetailView
import com.otaz.imdbmovieapp.presentation.components.util.IMAGE_HEIGHT
import com.otaz.imdbmovieapp.presentation.navigation.Screen
import com.otaz.imdbmovieapp.presentation.theme.AppTheme
import com.otaz.imdbmovieapp.presentation.ui.movie_detail.MovieEvent.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel,
    movieId: Int?,
) {
    if(movieId == null){
        ShimmerMovieListCardItem(
                imageHeight = 250.dp,
                padding = 8.dp
            )
    } else {
        // The purpose of the following block is to make sure the movie is only called once.
        // Before, this was being performed in the onCreate function but we are no longer using fragments.
        val scaffoldState = rememberScaffoldState()
        val loading = viewModel.loading.value
        val movieTmdb = viewModel.movieTmdb.value
        val movieOmdb = viewModel.movieOmdb.value
        val reviews = viewModel.reviews.value
        val page = viewModel.page.value
        val configurations = viewModel.configurations.value
        val dialogQueue = viewModel.dialogQueue
        val onLoad = viewModel.onLoad.value

        if (!onLoad) {
            viewModel.onLoad.value = true
            viewModel.onTriggerEvent(GetTmdbMovieEvent(movieId))
        }

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
                    if (loading && movieTmdb == null && movieOmdb == null) {
                        ShimmerMovieCardItem(imageHeight = IMAGE_HEIGHT.dp)
                    } else if(!loading && movieTmdb == null && onLoad){
//                        TODO("Show Invalid Movie")
                    }else if(movieTmdb != null && movieOmdb != null) {
                        MovieDetailView(
                            loading = loading,
                            reviews = reviews,
                            onChangeReviewScrollPosition = viewModel::onChangeReviewScrollPosition,
                            onTriggerNextPage = { viewModel.onTriggerEvent( NextPageEvent) },
                            page = page,
                            configurations = configurations,
                            movieTmdb = movieTmdb,
                            movieOmdb = movieOmdb,
                        )
                    }
                }
            }
        }
    }
}