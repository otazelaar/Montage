package com.otaz.imdbmovieapp.interactors.movie

import com.otaz.imdbmovieapp.cache.model.MovieSpecEntityMapper
import com.otaz.imdbmovieapp.domain.data.DataState
import com.otaz.imdbmovieapp.domain.model.Movie
import com.otaz.imdbmovieapp.domain.model.MovieSpecs
import com.otaz.imdbmovieapp.network.MovieService
import com.otaz.imdbmovieapp.network.model.MovieSpecDao
import com.otaz.imdbmovieapp.network.model.MovieSpecDtoMapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * This use case handles all data conversions from the network to the cache so that
 * a view model only ever sees the [Movie] domain model.
 */

class GetMovie(
    private val movieService: MovieService,
    private val dtoMapper: MovieSpecDtoMapper,
){
    fun execute(
        apikey: String,
        id: String,
    ): Flow<DataState<MovieSpecs>> = flow {
        try {
            emit(DataState.loading())

            val movie = getMovieSpecsFromNetwork(
                apikey = apikey,
                id = id,
            )

            emit(DataState.success(movie))

        }catch (e: Exception){
            emit(DataState.error(e.message ?: "Unknown error"))
        }
    }

    // This can throw an exception if there is no network connection
    // This function gets DTOs from the network and converts them to Movie Objects
    private suspend fun getMovieSpecsFromNetwork(
        apikey: String,
        id: String,
    ): MovieSpecs{
        return dtoMapper.mapToDomainModel(
            movieService.get(
                apikey = apikey,
                id = id,
            )
        )
    }
}