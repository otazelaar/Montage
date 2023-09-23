package com.otaz.montage.domain.model

data class TmdbMovieSpecs(
    val adult: Boolean,
    val backdrop_path: String?,
    val budget: Int,
    val id: Int,
    val imdb_id: String?,
    val original_title: String,
    val overview: String?,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String,
    val revenue: Long?,
    val runtime: Int?,
    val title: String,
    val vote_average: Double,
    val vote_count: Int
)

///**
// * Maps [TmdbMovieSpecs] to [TmdbMovieSpecsEntity] so that the data can be cached in the room database
// */
//fun TmdbMovieSpecs.toTmdbMovieSpecsEntity(): TmdbMovieSpecsEntity {
//    return TmdbMovieSpecsEntity(
//        id = id,
//        adult = adult,
//        backdrop_path = backdrop_path,
//        overview = overview,
//        poster_path = poster_path,
//        release_date = release_date,
//        title = title,
//    )
//}