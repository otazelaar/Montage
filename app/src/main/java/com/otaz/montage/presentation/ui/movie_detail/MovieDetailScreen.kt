package com.otaz.montage.presentation.ui.movie_detail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.otaz.montage.presentation.components.ShimmerMovieCardItem
import com.otaz.montage.presentation.components.ShimmerMovieListCardItem
import com.otaz.montage.presentation.components.movie_detail.MovieDetailView
import com.otaz.montage.presentation.components.util.IMAGE_HEIGHT
import com.otaz.montage.presentation.theme.AppTheme
import com.otaz.montage.presentation.ui.movie_detail.MovieEvent.*

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
        val scaffoldState = rememberScaffoldState()
        val movieTmdb = viewModel.movieTmdb.value
        val movieOmdb = viewModel.movieOmdb.value
        val reviews = viewModel.reviews.value
        val page = viewModel.page.value
        val configurations = viewModel.configurations.value

        viewModel.onTriggerEvent(GetTmdbMovieEvent(movieId))

        AppTheme{
            Scaffold(
                scaffoldState = scaffoldState,
                snackbarHost = { scaffoldState.snackbarHostState }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (movieTmdb == null && movieOmdb == null) {
                        ShimmerMovieCardItem(imageHeight = IMAGE_HEIGHT.dp)
                    } else if(movieTmdb != null && movieOmdb != null) {
                        MovieDetailView(
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