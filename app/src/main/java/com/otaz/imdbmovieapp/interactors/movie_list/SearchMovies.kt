package com.otaz.imdbmovieapp.interactors.movie_list

import com.otaz.imdbmovieapp.cache.model.MovieEntityMapper
import com.otaz.imdbmovieapp.domain.data.DataState
import com.otaz.imdbmovieapp.domain.model.Movie
import com.otaz.imdbmovieapp.network.MovieService
import com.otaz.imdbmovieapp.network.model.MovieDao
import com.otaz.imdbmovieapp.network.model.MovieDtoMapper
import com.otaz.imdbmovieapp.util.MOVIE_PAGINATION_PAGE_SIZE
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * This use case handles all data conversions from the network to the cache so that
 * a view model only ever sees the [Movie] domain model.
 */

class SearchMovies(
    private val movieDao: MovieDao,
    private val movieService: MovieService,
    private val entityMapper: MovieEntityMapper,
    private val dtoMapper: MovieDtoMapper,
){
    fun execute(
        apikey: String,
        expression: String,
        page: Int,
        count: String,
    ): Flow<DataState<List<Movie>>> = flow {
        try {
            emit(DataState.loading())

            // just to show pagination/progress bar because api is fast
            delay(1000)

            val movies = getMoviesFromNetwork(
                apikey = apikey,
                expression = expression,
                count = count
            )

            // insert into the cache
            movieDao.insertMovies(entityMapper.toEntityList(movies))

            // query the cache
            val cacheResult = if(expression.isBlank()){
                movieDao.getAllMovies(
                    page = page,
                    pageSize = MOVIE_PAGINATION_PAGE_SIZE,
                )
            }else{
                movieDao.searchMovies(
                    query = expression,
                    page = page,
                    pageSize = count.toInt(),
                )
            }
            // Emit List<Movie> from the cache
            val list = entityMapper.fromEntityList(cacheResult)

            emit(DataState.success(list))

        }catch (e: Exception){
            emit(DataState.error(e.message ?: "Unknown error"))
        }
    }

    // This can throw an exception if there is no network connection
    // This function gets Dto's from the network and converts them to Movie Objects
    private suspend fun getMoviesFromNetwork(
        apikey: String,
        expression: String,
        count: String,
    ): List<Movie>{
        return dtoMapper.toDomainList(
            movieService.search(
                apikey = apikey,
                expression = expression,
                count = count,
            ).movies
        )
    }
}