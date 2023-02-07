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
    private val movieDao: MovieSpecDao,
    private val movieService: MovieService,
    private val entityMapper: MovieSpecEntityMapper,
    private val dtoMapper: MovieSpecDtoMapper,
){
    fun execute(
        apikey: String,
        id: String,
    ): Flow<DataState<MovieSpecs>> = flow {
        try {
            emit(DataState.loading())

            // just to show pagination/progress bar because api is fast
            delay(1000)

            var movie = getMovieSpecsFromCache(movieId = id)

            if (movie != null){
                emit(DataState.success(movie))
            } else {
                val networkMovieSpecs = getMovieSpecsFromNetwork(
                    apikey = apikey,
                    id = id.toString(),
                )

                movieDao.insertMovie(
                    entityMapper.mapFromDomainModel(networkMovieSpecs)
                )

                movie = getMovieSpecsFromCache(movieId = id)

                if (movie != null){
                    emit(DataState.success(movie))
                }else{
                    throw Exception("Unable to get the movie from the cache")
                }
            }
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

    private suspend fun getMovieSpecsFromCache(
        movieId: String,
    ): MovieSpecs?{
        return movieDao.getMovieById(movieId)?.let { movieSpecEntity ->
            entityMapper.mapToDomainModel(movieSpecEntity)
        }
    }
}