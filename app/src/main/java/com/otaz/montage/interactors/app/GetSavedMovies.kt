package com.otaz.montage.interactors.app

import com.otaz.montage.cache.model.entitiesToMovie
import com.otaz.montage.domain.data.DataState
import com.otaz.montage.domain.model.Movie
import com.otaz.montage.network.model.MovieDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSavedMovies(
    private val movieDao: MovieDao,
) {
    fun execute(): Flow<DataState<List<Movie>>> = flow {
        try {
            emit(DataState.loading())

            val listOfMovieEntity = movieDao.getAllMovies()
            val cachedMovies = entitiesToMovie(listOfMovieEntity)

            emit(DataState.success(cachedMovies))
        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "GetSavedMovies UseCase Error"))
        }
    }
}