package com.otaz.montage.interactors.app

import android.util.Log
import com.otaz.montage.cache.MovieDao
import com.otaz.montage.domain.model.Movie
import com.otaz.montage.domain.model.toMovieWatchListEntity
import com.otaz.montage.util.TAG

class AddMovieToWatchList(
    private val movieDao: MovieDao,
) {
    suspend fun execute(
        movie: Movie
    ) {
        try {
            val movieToBeCached = movie.toMovieWatchListEntity()
            // the purpose of the following line is to create an order that the movies were added by
            var orderAdded = movieToBeCached.orderAdded+1
            orderAdded++
            movieDao.insertMovieWatchList(movieToBeCached)
            Log.i(TAG, "AddMovieToWatchList movie added to watchlist: $orderAdded")

        } catch (e: Exception) {
            Log.e(TAG, "AddMovieToWatchList UseCase Error: $e")
        }
    }
}