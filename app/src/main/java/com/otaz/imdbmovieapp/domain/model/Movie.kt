package com.otaz.imdbmovieapp.domain.model

import java.io.Serializable

data class Movie (
    val crew: String? = null,
    val fullTitle: String? = null,
    val id: String? = null,
    val imDbRating: String? = null,
    val imDbRatingCount: String? = null,
    val image: String? = null,
    val rank: String? = null,
    val title: String? = null,
    val year: String? = null,
) : Serializable