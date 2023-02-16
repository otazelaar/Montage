package com.otaz.imdbmovieapp.interactors.movie_list

import android.util.Log
import com.otaz.imdbmovieapp.cache.model.MovieEntityMapper
import com.otaz.imdbmovieapp.domain.data.DataState
import com.otaz.imdbmovieapp.domain.model.Movie
import com.otaz.imdbmovieapp.network.model.MovieDao
import com.otaz.imdbmovieapp.util.MOVIE_PAGINATION_PAGE_SIZE
import com.otaz.imdbmovieapp.util.TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RestoreMovies (
    private val movieDao: MovieDao,
    private val entityMapper: MovieEntityMapper,
){
    fun execute(
        page: Int,
        query: String,
    ): Flow<DataState<List<Movie>>> = flow {
        try {
            emit(DataState.loading())

            val cacheResult = if (query.isBlank()){
                movieDao.restoreAllMovies(
                    pageSize = MOVIE_PAGINATION_PAGE_SIZE,
                    page = page
                )
            }else{
                movieDao.restoreMovies(
                    pageSize = MOVIE_PAGINATION_PAGE_SIZE,
                    page = page,
                    query = query
                )
            }

            val list = entityMapper.fromEntityList(cacheResult)
            emit(DataState.success(list))

        }catch (e: Exception){
            Log.e(TAG, "execute: ${e.message}")
            emit(DataState.error(e.message?: "Unknown error"))
        }
    }
}