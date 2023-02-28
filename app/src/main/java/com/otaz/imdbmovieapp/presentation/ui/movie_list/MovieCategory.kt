package com.otaz.imdbmovieapp.presentation.ui.movie_list

import com.otaz.imdbmovieapp.presentation.ui.movie_list.MovieCategory.*

enum class MovieCategory(val value: String){
    GET_MOST_POPULAR_MOVIES("Popular"),
    GET_UPCOMING_MOVIES("Upcoming"),
    GET_TOP_RATED_MOVIES("Top Rated"),
}

fun getAllMovieCategories(): List<MovieCategory>{
    return listOf(GET_MOST_POPULAR_MOVIES, GET_UPCOMING_MOVIES, GET_TOP_RATED_MOVIES)
}

fun getMovieCategory(value: String): MovieCategory? {
    val map = values().associateBy(MovieCategory::value)
    return map[value]
}