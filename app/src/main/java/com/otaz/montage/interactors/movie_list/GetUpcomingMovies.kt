package com.otaz.montage.interactors.movie_list

import com.otaz.montage.domain.data.DataState
import com.otaz.montage.domain.model.Movie
import com.otaz.montage.network.TmdbApiService
import com.otaz.montage.network.model.MovieDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUpcomingMovies(
    private val tmdbApiService: TmdbApiService,
    private val dtoMapper: MovieDtoMapper,
){
    fun execute(
        apikey: String,
        page: Int,
    ): Flow<DataState<List<Movie>>> = flow {
        try {
            emit(DataState.loading())

            val movies = getMoviesFromNetwork(
                apikey = apikey,
                page = page,
            )

            emit(DataState.success(movies))

        }catch (e: Exception){
            emit(DataState.error(e.message ?: "GetUpcomingMovies: Unknown error"))
        }
    }

    // This can throw an exception if there is no network connection
    // This function gets Dto's from the network and converts them to Movie Objects
    private suspend fun getMoviesFromNetwork(
        apikey: String,
        page: Int,
    ): List<Movie>{
        return dtoMapper.toDomainList(
            tmdbApiService.getUpcomingMovies(
                apikey = apikey,
                page = page,
            ).movies
        ).filter {
            it.poster_path != null &&
            !it.adult
        }
    }
}