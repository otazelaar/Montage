package com.otaz.montage.interactors.app

import com.otaz.montage.domain.data.DataState
import com.otaz.montage.domain.model.Movie
import com.otaz.montage.cache.MovieDao
import com.otaz.montage.cache.model.entitiesToMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSavedMovies(
    private val movieDao: MovieDao,
) {
    fun execute(): Flow<DataState<List<Movie>>> = flow {
        try {
            emit(DataState.loading())

            // TODO() maybe check if database is full before calling it
            val listOfSavedMovieEntities = movieDao.getWatchListBoolean()
            val savedMovies = entitiesToMovie(listOfSavedMovieEntities)

            emit(DataState.success(savedMovies))

        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "GetSavedMovies UseCase Error"))
        }
    }
}