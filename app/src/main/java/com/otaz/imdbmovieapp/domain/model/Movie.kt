package com.otaz.imdbmovieapp.domain.model

/**
 * The following data class represents one movie in a list of movies
 * It has been deserialized by this point and is ready to be accessed by the UI
 */

data class Movie(
    val adult: Boolean,
    val backdrop_path: String?,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String,
    val title: String,
    val vote_count: Int,
)