package com.otaz.imdbmovieapp.interactors.movie_detail

import com.otaz.imdbmovieapp.domain.data.DataState
import com.otaz.imdbmovieapp.domain.model.MovieReview
import com.otaz.imdbmovieapp.network.TmdbApiService
import com.otaz.imdbmovieapp.network.model.MovieReviewsDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMovieReviews(
    private val tmdbApiService: TmdbApiService,
    private val movieReviewsDtoMapper: MovieReviewsDtoMapper,
){
    fun execute(
        id: Int,
        apikey: String,
        page: Int,
    ): Flow<DataState<List<MovieReview>>> = flow {
        try {
            emit(DataState.loading())

            val movies = getMovieReviewsFromNetwork(
                id = id,
                apikey = apikey,
                page = page,
            )

            emit(DataState.success(movies))

        }catch (e: Exception){
            emit(DataState.error(e.message ?: "GetMovieReviews: Unknown error"))
        }
    }

    // This can throw an exception if there is no network connection
    // This function gets Dto's from the network and converts them to Movie Objects
    private suspend fun getMovieReviewsFromNetwork(
        id: Int,
        apikey: String,
        page: Int,
    ): List<MovieReview>{
        return movieReviewsDtoMapper.toDomainList(
            tmdbApiService.getMovieReviews(
                id = id,
                apikey = apikey,
                page = page,
            ).movieReviewDtos
        )
    }
}