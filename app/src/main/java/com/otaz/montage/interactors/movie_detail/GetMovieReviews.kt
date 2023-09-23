package com.otaz.montage.interactors.movie_detail

import com.otaz.montage.domain.data.DataState
import com.otaz.montage.domain.model.MovieReview
import com.otaz.montage.network.TmdbApiService
import com.otaz.montage.network.model.toMovieReview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMovieReviews(
    private val tmdbApiService: TmdbApiService,
) {
    fun execute(
        id: Int,
        apikey: String,
        page: Int,
    ): Flow<DataState<List<MovieReview>>> = flow {
        emit(DataState.loading())

        // may need db caching
        // handle errors more deeply

        val statusResult = runCatching {
            getMovieReviewsFromNetwork(id, apikey, page)
        }.onSuccess { status ->
            emit(DataState.success(status))
        }.onFailure { error: Throwable ->
            emit(DataState.error(error.message.toString()))
            println("Go network error: ${error.message}")
        }

        println("StatusResult is: $statusResult")
    }

    private suspend fun getMovieReviewsFromNetwork(
        id: Int,
        apikey: String,
        page: Int,
    ): List<MovieReview> {
        return tmdbApiService.getMovieReviews(
            id = id,
            apikey = apikey,
            page = page,
        ).movieReviewDtos.map { it.toMovieReview() }
    }
}
