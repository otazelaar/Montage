package com.otaz.imdbmovieapp.interactors.app

import android.util.Log
import com.otaz.imdbmovieapp.cache.model.MovieEntityMapper
import com.otaz.imdbmovieapp.network.model.MovieDao
import com.otaz.imdbmovieapp.util.TAG

class DeleteMovie(
    private val movieDao: MovieDao,
) {
    suspend fun execute(
        id: String
    ) {
        try {
            movieDao.deleteMovie(id)

            Log.i(TAG, "DeleteMovieUseCase Success: $id")

        } catch (e: Exception) {
            Log.e(TAG, "DeleteMovieUseCase UseCase Error: $e")
        }
    }
}