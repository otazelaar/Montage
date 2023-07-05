package com.otaz.montage.interactors.app

import android.util.Log
import com.otaz.montage.cache.model.MovieEntityMapper
import com.otaz.montage.domain.data.DataState
import com.otaz.montage.domain.model.Movie
import com.otaz.montage.network.model.MovieDao
import com.otaz.montage.util.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSavedMovies(
    private val movieDao: MovieDao,
    private val movieEntityMapper: MovieEntityMapper,
) {
    fun execute(): Flow<DataState<List<Movie>>> = flow {
        try {
            emit(DataState.loading())

            val savedMovies = movieDao.getAllMovies()
            val cachedMovies = movieEntityMapper.fromEntityList(savedMovies)

            emit(DataState.success(cachedMovies))
            Log.i(TAG, "GetSavedMovies UseCase Success: $cachedMovies")

        } catch (e: Exception) {
            Log.e(TAG, "GetSavedMovies UseCase Error: $e")
            emit(DataState.error(e.message ?: "GetSavedMovies UseCase Error"))
        }
    }
}