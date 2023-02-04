package com.otaz.imdbmovieapp.domain.model

data class Movie (
    val id: String,
    val year: String?,
    val imageURL: String,
    val title: String,
    val type: String?,
)