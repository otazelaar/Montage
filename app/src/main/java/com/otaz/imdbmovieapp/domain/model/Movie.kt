package com.otaz.imdbmovieapp.domain.model

import java.io.Serializable

data class Movie (
    val id: String,
    val imdbRating: String? = null,
    val imageURL: String,
    val title: String,
    val description: String,
) : Serializable