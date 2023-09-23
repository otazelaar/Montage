package com.otaz.montage.interactors.movie_list

import com.otaz.montage.domain.data.DataState
import com.otaz.montage.domain.model.Movie
import com.otaz.montage.network.TmdbApiService
import com.otaz.montage.network.model.toMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchMovies(
    private val tmdbApiService: TmdbApiService,
) {
    fun execute(
        apikey: String,
        query: String,
        page: Int,
    ): Flow<DataState<List<Movie>>> = flow {
        emit(DataState.loading())

        // might need db caching

        val statusResult = runCatching {
            getMoviesFromNetwork(apikey, query, page)
        }.onSuccess { status ->
            emit(DataState.success(status))
        }.onFailure { error: Throwable ->
            emit(DataState.error(error.message.toString()))
            println("Go network error: ${error.message}")
        }

        println("StatusResult is: $statusResult")
    }

    private suspend fun getMoviesFromNetwork(
        apikey: String,
        query: String,
        page: Int,
    ): List<Movie> {
        return tmdbApiService.searchMovies(
            apikey = apikey,
            query = query,
            page = page,
        ).moviesDto.map { it.toMovie() }.filter {
            it.poster_path != null &&
            it.backdrop_path != null &&
            !it.adult
        }
    }
}