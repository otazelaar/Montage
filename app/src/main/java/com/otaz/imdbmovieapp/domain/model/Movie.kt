package com.otaz.imdbmovieapp.domain.model

import java.io.Serializable

data class Movie (
    val id: String? = null,
    val imdbRating: String? = null,
    val imageURL: String? = null,
    val title: String? = null,
    val description: String? = null,
) : Serializable

// maybe get rid of serializable. this is not in mitch's equivalent data class. Is it necessary?