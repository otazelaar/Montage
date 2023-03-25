package com.otaz.imdbmovieapp.interactors.app

import android.util.Log
import com.otaz.imdbmovieapp.cache.model.MovieEntityMapper
import com.otaz.imdbmovieapp.domain.model.Movie
import com.otaz.imdbmovieapp.network.model.MovieDao
import com.otaz.imdbmovieapp.util.TAG

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