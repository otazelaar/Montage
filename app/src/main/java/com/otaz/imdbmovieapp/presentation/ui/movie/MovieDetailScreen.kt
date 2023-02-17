//package com.otaz.imdbmovieapp.presentation.ui.movie
//
//import android.annotation.SuppressLint
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material.Scaffold
//import androidx.compose.material.rememberScaffoldState
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.otaz.imdbmovieapp.presentation.components.IMAGE_HEIGHT
//import com.otaz.imdbmovieapp.presentation.components.MovieView
//import com.otaz.imdbmovieapp.presentation.components.ShimmerMovieCardItem
//import com.otaz.imdbmovieapp.presentation.ui.movie.MovieEvent.GetMovieEvent
//import com.otaz.imdbmovieapp.presentation.theme.AppTheme
//
//@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
//@Composable
//fun MovieDetailScreen(
//    isNetworkAvailable: Boolean,
//    viewModel: MovieDetailViewModel,
//    movieId: String?,
//) {
//    if (movieId == null) {
////        TODO("Show invalid Movie")
//    } else {
//        // The purpose of the following block is to make sure the movie is only called once.
//        // Before, this was being performed in the onCreate function but we are no longer using fragments.
//        val onLoad = viewModel.onLoad.value
//        if (!onLoad) {
//            viewModel.onLoad.value = true
//            viewModel.onTriggerEvent(GetMovieEvent(movieId))
//        }
//        val loading = viewModel.loading.value
//
//        val movie = viewModel.movie.value
//
//        val dialogQueue = viewModel.dialogQueue
//
//        val scaffoldState = rememberScaffoldState()
//
//        AppTheme(
//            displayProgressBar = loading,
//            isNetworkAvailable = isNetworkAvailable,
//            scaffoldState = scaffoldState,
//            dialogQueue = dialogQueue.queue.value
//        ) {
//            Scaffold(
//                scaffoldState = scaffoldState,
//                snackbarHost = {
//                    scaffoldState.snackbarHostState
//                }
//            ) {
//                Box(
//                    modifier = Modifier.fillMaxSize()
//                ) {
//                    if (loading && movie == null) {
//                        ShimmerMovieCardItem(imageHeight = IMAGE_HEIGHT.dp)
//                    } else if(!loading && movie == null && onLoad){
////                        TODO("Show Invalid Recipe")
//                    }
//                    else {
//                        movie?.let { MovieView(movie = it) }
//                    }
//                }
//            }
//        }
//    }
//}
