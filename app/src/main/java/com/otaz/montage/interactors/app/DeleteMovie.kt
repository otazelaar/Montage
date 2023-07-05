package com.otaz.montage.interactors.app

import android.util.Log
import com.otaz.montage.network.model.MovieDao
import com.otaz.montage.util.TAG

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