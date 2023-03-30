package com.otaz.imdbmovieapp.interactors.movie_game

import android.util.Log
import com.otaz.imdbmovieapp.cache.model.MovieEntityMapper
import com.otaz.imdbmovieapp.domain.data.DataState
import com.otaz.imdbmovieapp.domain.model.Movie
import com.otaz.imdbmovieapp.network.model.MovieDao
import com.otaz.imdbmovieapp.util.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * The purpose of this UseCase is to retrieve a random movie from the TopRatedMovies API search preset
 * and display this movie on a screen for the user to guess the correct year the movie was released.
 *
 * Maybe make a few years to offer them as 4 multiple choice answers where one is the correct one?
 */
class GetRandomTopRatedMovie(
    private val movieDao: MovieDao,
    private val movieEntityMapper: MovieEntityMapper,
){
    fun execute(): Flow<DataState<Movie>> = flow {
        try {
            emit(DataState.loading())

            //right now, we are not getting "RandomTopRatedMovies". We are getting only movies saved
            // in the room database which can be anything I save. In the future we will make this get anything.
            val randomMovieFromDatabase = movieDao.getRandomMovie()
            if (randomMovieFromDatabase != null) {
                val randomMovie = movieEntityMapper.mapToDomainModel(randomMovieFromDatabase)
                emit(DataState.success(randomMovie))
            }

            Log.i(TAG, "GetRandomTopRatedMovieUseCase: Success: $")

        } catch (e: Exception) {
            Log.e(TAG, "GetRandomTopRatedMovieUseCase: Error: $e")
        }
    }
}