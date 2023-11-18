package com.otaz.montage.domain.model

import com.otaz.montage.cache.model.MovieEntity

/**
 * The following data class represents one movie in a list of movies
 * It has been deserialized by this point and is ready to be accessed by the UI
 */

data class Movie(
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String?,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String?,
    val title: String,
    val isInWatchlist: Boolean,
    val hasBeenWatched: Boolean,
    val timeSavedToWatchList: String,
)

/**
 * Maps [Movie] to [MovieEntity] so that the data can be cached in the room database
 */
fun Movie.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        adult = adult,
        backdrop_path = backdrop_path,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        isInWatchlist = isInWatchlist,
        hasBeenWatched = hasBeenWatched,
        timeSavedToWatchList = timeSavedToWatchList, // (Date but convert to String to store in DB).
    )
}