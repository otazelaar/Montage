package com.otaz.montage.interactors.app

import android.util.Log
import com.otaz.montage.domain.model.Movie
import com.otaz.montage.domain.model.toMovieEntity
import com.otaz.montage.cache.MovieDao
import com.otaz.montage.util.TAG

class CacheMovie(
    private val movieDao: MovieDao,
) {
    suspend fun execute(
        movie: Movie
    ) {
        try {
            val movieToBeCached = movie.toMovieEntity()
            movieDao.insertMovie(movieToBeCached)

        } catch (e: Exception) {
            Log.e(TAG, "CacheMovie UseCase Error: $e")
        }
    }
}