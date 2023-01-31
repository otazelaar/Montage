package com.otaz.imdbmovieapp.presentation.navigation

sealed class Screen(
    val route: String,
){
    object MovieList: Screen("movieList")

    object MovieDetail: Screen("movieDetail")
}