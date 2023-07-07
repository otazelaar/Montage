package com.otaz.montage.interactors.movie_list

import com.otaz.montage.domain.data.DataState
import com.otaz.montage.domain.model.Movie
import com.otaz.montage.network.TmdbApiService
import com.otaz.montage.network.model.MovieDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMostPopularMovies (
    private val tmdbApiService: TmdbApiService,
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

    private suspend fun getMostPopularMoviesFromNetwork(
        apikey: String,
        sortBy: String,
        page: Int,
    ): List<Movie>{
        return dtoMapper.toDomainList(
            tmdbApiService.getMostPopularMovies(
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
