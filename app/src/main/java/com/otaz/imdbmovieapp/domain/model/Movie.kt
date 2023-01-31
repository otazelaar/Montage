package com.otaz.imdbmovieapp.domain.model

data class Movie (
    val id: String,
    val imdbRating: String?,
    val imageURL: String,
    val title: String,
    val description: String?,
//    val keywords: List<String> = listOf(),
)