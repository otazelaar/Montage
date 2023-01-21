package com.otaz.imdbmovieapp.presentation.movie_list

import com.otaz.imdbmovieapp.presentation.movie_list.MovieCategory.*

enum class MovieCategory(val value: String){
    CHICKEN("Chicken"),
    BEEF("Beef"),
    SOUP("Soup"),
    DESSERT("Dessert"),
    VEGETARIAN("Vegetarian"),
    MILK("Milk"),
    VEGAN("Vegan"),
    PIZZA("Pizza"),
    DONUT("Donut"),
}

fun getAllMovieCategories(): List<MovieCategory>{
    return listOf(CHICKEN, BEEF, SOUP, DESSERT, VEGETARIAN, MILK, VEGAN, PIZZA, DONUT)
}

fun getMovieCategory(value: String): MovieCategory? {
    val map = values().associateBy(MovieCategory::value)
    return map[value]
}