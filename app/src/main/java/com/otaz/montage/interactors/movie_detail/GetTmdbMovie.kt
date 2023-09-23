package com.otaz.montage.interactors.movie_detail

import com.otaz.montage.domain.data.DataState
import com.otaz.montage.domain.model.TmdbMovieSpecs
import com.otaz.montage.network.TmdbApiService
import com.otaz.montage.network.model.toTmdbMovieSpecs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTmdbMovie(
    private val tmdbApiService: TmdbApiService,
) {
    fun execute(
        tmdb_apikey: String,
        id: Int,
    ): Flow<DataState<TmdbMovieSpecs>> = flow {
        emit(DataState.loading())

        // may need db caching
        // handle errors more deeply

        val statusResult = runCatching {
            getTmdbMovieSpecsFromNetwork(tmdb_apikey, id)
        }.onSuccess { status ->
            emit(DataState.success(status))
        }.onFailure { error: Throwable ->
            emit(DataState.error(error.message.toString()))
            println("Go network error: ${error.message}")
        }

        println("StatusResult is: $statusResult")
    }

    private suspend fun getTmdbMovieSpecsFromNetwork(
        tmdb_apikey: String,
        tmdb_id: Int,
    ): TmdbMovieSpecs {
        return tmdbApiService.getMovieSpecs(
            apikey = tmdb_apikey,
            id = tmdb_id,
        ).toTmdbMovieSpecs()
    }
}
