package com.otaz.montage.interactors.movie_list

import com.otaz.montage.domain.data.DataState
import com.otaz.montage.domain.model.Movie
import com.otaz.montage.network.TmdbApiService
import com.otaz.montage.network.model.toMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMostPopularMovies(
    private val tmdbApiService: TmdbApiService,
) {
    fun execute(
        apikey: String,
        sortBy: String,
        page: Int,
    ): Flow<DataState<List<Movie>>> = flow {
        emit(DataState.loading())

        // still need to check database first and call network if the movies are not already there
        // then cache new books in database once the network is called
        // aside from the above two lines of code, this should work for now

        val statusResult = runCatching {
            getMostPopularMoviesFromNetwork(apikey, sortBy, page)
        }.onSuccess { status ->
            emit(DataState.success(status))
        }.onFailure { error: Throwable ->
            emit(DataState.error(error.message.toString()))
            println("Go network error: ${error.message}")
        }

        println("StatusResult is: $statusResult")

    }
    private suspend fun getMostPopularMoviesFromNetwork(
        apikey: String,
        sortBy: String,
        page: Int,
    ): List<Movie>{
        return tmdbApiService.getMostPopularMovies(
                apikey = apikey,
                sortBy = sortBy,
                page = page,
            ).moviesDto.map {
                it.toMovie()
        }.filter {
            it.poster_path != null &&
            it.backdrop_path != null &&
            !it.adult
        }
    }
}
