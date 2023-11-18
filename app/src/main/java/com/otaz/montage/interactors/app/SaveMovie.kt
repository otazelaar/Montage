package com.otaz.montage.interactors.app

import android.util.Log
import com.otaz.montage.cache.MovieDao
import com.otaz.montage.domain.model.Movie
import com.otaz.montage.domain.model.toMovieEntity
import com.otaz.montage.util.TAG

class SaveMovie(
    private val movieDao: MovieDao,
) {
    suspend fun execute(
        movie: Movie
    ) {
        try {
            val movieToBeSaved = movie.toMovieEntity()
            movieDao.insertMovie(movieToBeSaved)

        } catch (e: Exception) {
            Log.e(TAG, "SaveMovie UseCase Error: $e")
        }
    }
}