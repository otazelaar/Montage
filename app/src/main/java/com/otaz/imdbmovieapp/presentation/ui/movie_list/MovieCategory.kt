package com.otaz.imdbmovieapp.presentation.ui.movie_list

import com.otaz.imdbmovieapp.presentation.ui.movie_list.MovieCategory.*

enum class MovieCategory(val value: String){
    MOVIE_ONE("Iron Man"),
    MOVIE_TWO("Interstellar"),
    MOVIE_THREE("2001: A Space Odyssey"),
    MOVIE_FOUR("Dessert"),
    MOVIE_FIVE("Vegetarian"),
    MOVIE_SIX("Milk"),
    MOVIE_SEVEN("Vegan"),
    MOVIE_EIGHT("Pizza"),
    MOVIE_NINE("Donut"),
}

fun getAllMovieCategories(): List<MovieCategory>{
    return listOf(MOVIE_ONE, MOVIE_TWO, MOVIE_THREE, MOVIE_FOUR, MOVIE_FIVE, MOVIE_SIX, MOVIE_SEVEN, MOVIE_EIGHT, MOVIE_NINE)
}

fun getMovieCategory(value: String): MovieCategory? {
    val map = values().associateBy(MovieCategory::value)
    return map[value]
}