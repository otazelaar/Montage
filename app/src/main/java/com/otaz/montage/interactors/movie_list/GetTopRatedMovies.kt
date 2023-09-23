package com.otaz.montage.interactors.movie_list

import com.otaz.montage.domain.data.DataState
import com.otaz.montage.domain.model.Movie
import com.otaz.montage.network.TmdbApiService
import com.otaz.montage.network.model.toMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTopRatedMovies(
    private val tmdbApiService: TmdbApiService,
) {
    fun execute(
        apikey: String,
        page: Int,
    ): Flow<DataState<List<Movie>>> = flow {
        emit(DataState.loading())

        // may need db caching
        // handle errors more deeply

        val statusResult = runCatching {
            getTopRatedMoviesFromNetwork(apikey, page)
        }.onSuccess { status ->
            emit(DataState.success(status))
        }.onFailure { error: Throwable ->
            emit(DataState.error(error.message.toString()))
            println("Go network error: ${error.message}")
        }

        println("StatusResult is: $statusResult")
    }

    private suspend fun getTopRatedMoviesFromNetwork(
        apikey: String,
        page: Int,
    ): List<Movie> {
        return tmdbApiService.getTopRatedMovies(
            apikey = apikey,
            page = page,
        ).moviesDto.map { it.toMovie() }.filter {
            it.poster_path != null &&
            it.backdrop_path != null &&
            !it.adult
        }
    }
}