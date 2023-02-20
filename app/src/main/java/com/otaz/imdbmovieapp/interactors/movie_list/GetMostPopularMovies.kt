package com.otaz.imdbmovieapp.interactors.movie_list

import com.otaz.imdbmovieapp.domain.data.DataState
import com.otaz.imdbmovieapp.domain.model.Movie
import com.otaz.imdbmovieapp.network.MovieService
import com.otaz.imdbmovieapp.network.model.MovieDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMostPopularMovies (
    private val movieService: MovieService,
    private val dtoMapper: MovieDtoMapper,
){
    fun execute(
        apikey: String,
        sortBy: String,
        page: Int,
    ): Flow<DataState<List<Movie>>> = flow {
        try {
            emit(DataState.loading())

            val movies = getMostPopularMoviesFromNetwork(
                apikey = apikey,
                sortBy = sortBy,
                page = page,
            )

            emit(DataState.success(movies))

        }catch (e: Exception){
            emit(DataState.error(e.message ?: "GetMostPopularMovies: Unknown error"))
        }
    }

    // This can throw an exception if there is no network connection
    // This function gets Dto's from the network and converts them to Movie Objects
    private suspend fun getMostPopularMoviesFromNetwork(
        apikey: String,
        sortBy: String,
        page: Int,
    ): List<Movie>{
        return dtoMapper.toDomainList(
            movieService.getMostPopularMovies(
                apikey = apikey,
                sortBy = sortBy,
                page = page,
            ).movies
        ).filter {
            it.poster_path != null &&
            it.backdrop_path != null &&
            !it.adult
        }
    }
}
