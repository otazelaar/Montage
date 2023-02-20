package com.otaz.imdbmovieapp.presentation.ui.movie_list

import com.otaz.imdbmovieapp.presentation.ui.movie_list.MovieCategory.*

enum class MovieCategory(val value: String){
    GET_MOST_POPULAR_MOVIES("Popular"),
    GET_UPCOMING_MOVIES("Upcoming"),
    MOVIE_ONE("Iron Man"),
    MOVIE_TWO("Interstellar"),
    MOVIE_FOUR("Dessert"),
    MOVIE_FIVE("Vegetarian"),
    MOVIE_SIX("Milk"),
    MOVIE_SEVEN("Vegan"),
    MOVIE_NINE("Donut"),
}

fun getAllMovieCategories(): List<MovieCategory>{
    return listOf(GET_MOST_POPULAR_MOVIES, GET_UPCOMING_MOVIES, MOVIE_ONE, MOVIE_TWO, MOVIE_FOUR, MOVIE_FIVE, MOVIE_SIX, MOVIE_SEVEN, MOVIE_NINE)
}

fun getMovieCategory(value: String): MovieCategory? {
    val map = values().associateBy(MovieCategory::value)
    return map[value]
}