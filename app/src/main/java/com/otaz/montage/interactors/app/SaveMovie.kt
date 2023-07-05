package com.otaz.montage.interactors.app

import android.util.Log
import com.otaz.montage.cache.model.MovieEntityMapper
import com.otaz.montage.domain.model.Movie
import com.otaz.montage.network.model.MovieDao
import com.otaz.montage.util.TAG

class SaveMovie(
    private val movieDao: MovieDao,
    private val movieEntityMapper: MovieEntityMapper,
) {
    suspend fun execute(
        movie: Movie
    ) {
        try {
            val movieToBeCached = movieEntityMapper.mapFromDomainModel(movie)
            movieDao.insertMovie(movie = movieToBeCached)
            val movieID = movie.id.toString()
            Log.i(TAG, "SaveMovieUseCase Success: ${movieDao.getMovieById(movieID)}")

        } catch (e: Exception) {
            Log.e(TAG, "SaveMovie UseCase Error: $e")
        }
    }
}