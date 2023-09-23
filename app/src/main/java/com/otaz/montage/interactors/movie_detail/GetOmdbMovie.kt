package com.otaz.montage.interactors.movie_detail

import com.otaz.montage.domain.data.DataState
import com.otaz.montage.domain.model.OmdbMovieSpecs
import com.otaz.montage.network.OmdbApiService
import com.otaz.montage.network.model.toOmdbMovieSpecs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetOmdbMovie(
    private val omdbApiService: OmdbApiService,
) {
    fun execute(
        omdb_apikey: String,
        omdb_id: String,
    ): Flow<DataState<OmdbMovieSpecs>> = flow {
        emit(DataState.loading())

//         may need db caching to be offline first. remember this api is critical for allowing other
//         api to function

        // may need db caching
        // handle errors more deeply

        val statusResult = runCatching {
            getOmdbMovieSpecsFromNetwork(omdb_apikey, omdb_id)
        }.onSuccess { status ->
            emit(DataState.success(status))
        }.onFailure { error: Throwable ->
            emit(DataState.error(error.message.toString()))
            println("Go network error: ${error.message}")
        }

        println("StatusResult is: $statusResult")
    }

    private suspend fun getOmdbMovieSpecsFromNetwork(
        omdb_apikey: String,
        omdb_id: String,
    ): OmdbMovieSpecs {
        return omdbApiService.get(
            apikey = omdb_apikey,
            id = omdb_id,
        ).toOmdbMovieSpecs()
    }
}
