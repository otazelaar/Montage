package com.otaz.imdbmovieapp.interactors.movie_list

import com.otaz.imdbmovieapp.domain.data.DataState
import com.otaz.imdbmovieapp.domain.model.Movie
import com.otaz.imdbmovieapp.network.MovieService
import com.otaz.imdbmovieapp.network.model.MovieDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchMovies(
    private val movieService: MovieService,
    private val dtoMapper: MovieDtoMapper,
){
    fun execute(
        apikey: String,
        query: String,
    ): Flow<DataState<List<Movie>>> = flow {
        try {
            emit(DataState.loading())

            val movies = getMoviesFromNetwork(
                apikey = apikey,
                query = query,
            )

            emit(DataState.success(movies))

        }catch (e: Exception){
            emit(DataState.error(e.message ?: "SearchMovies: Unknown error"))
        }
    }

    // This can throw an exception if there is no network connection
    // This function gets Dto's from the network and converts them to Movie Objects
    private suspend fun getMoviesFromNetwork(
        apikey: String,
        query: String,
    ): List<Movie>{
        return dtoMapper.toDomainList(
            movieService.search(
                apikey = apikey,
                query = query,
            ).movies
        ).filter {
            it.poster_path != null
        }
    }
}