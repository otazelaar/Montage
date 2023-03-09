package com.otaz.imdbmovieapp.presentation.navigation

/**
 * This class is used to define routes for compose only navigation.
 * The MovieDetail object below can be appended as a string to a TMDB movie ID to navigate to the MovieDetailScreen.
 * Navigation back to the MovieListScreen is performed using the back button.
 */

sealed class Screen(
    val route: String,
){
    object MovieList: Screen("movieList")
    object MovieDetail: Screen("movieDetail")
}