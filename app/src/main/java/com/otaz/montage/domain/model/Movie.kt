package com.otaz.montage.domain.model

/**
 * The following data class represents one movie in a list of movies
 * It has been deserialized by this point and is ready to be accessed by the UI
 */

data class Movie(
    val id: Int,
    val adult: Boolean,
    val overview: String,
    val poster_path: String?,
    val release_date: String?,
    val title: String,
)