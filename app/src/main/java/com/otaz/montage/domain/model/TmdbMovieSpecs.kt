package com.otaz.montage.domain.model

data class TmdbMovieSpecs(
    val adult: Boolean,
    val backdrop_path: String?,
    val budget: Int,
    val homepage: String,
    val id: Int,
    val imdb_id: String?,
    val original_language: String,
    val original_title: String,
    val overview: String?,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String,
    val revenue: Long?,
    val runtime: Int?,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)