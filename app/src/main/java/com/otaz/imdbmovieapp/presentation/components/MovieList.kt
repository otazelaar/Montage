package com.otaz.imdbmovieapp.presentation.components

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.otaz.imdbmovieapp.R
import com.otaz.imdbmovieapp.domain.model.Movie
import com.otaz.imdbmovieapp.presentation.components.util.SnackbarController
import com.otaz.imdbmovieapp.presentation.movie_list.MovieListEvent.*
import com.otaz.imdbmovieapp.presentation.movie_list.PAGE_SIZE
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieList(
    loading: Boolean,
    movies: List<Movie>,
    onChangeMovieScrollPosition: (Int) -> Unit,
    onTriggerNextPage: () -> Unit,
    page: Int,
    scaffoldState: ScaffoldState,
    snackbarController: SnackbarController,
    navController: NavController,
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.surface)
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
                    if((index + 1) >= (page * PAGE_SIZE) && !loading){
                        onTriggerNextPage()
                    }
                    MovieCard(
                        movie = movie,
                        onClick = {
                            if(movie.id != null){
                                val bundle = Bundle()
                                bundle.putString("movieId", movie.id)
                                navController.navigate(R.id.viewMovie, bundle)
                            }else{
                                //this error is not happening so that is good
                                snackbarController.getScope().launch {
                                    snackbarController.showSnackbar(
                                        scaffoldState = scaffoldState,
                                        message = "Movie Error",
                                        actionLabel = "Ok",
                                    )
                                }

                            }
                        })
                }
            }
        }


    }
}