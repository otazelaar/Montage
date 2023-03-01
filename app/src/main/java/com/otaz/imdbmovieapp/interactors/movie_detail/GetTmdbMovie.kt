package com.otaz.imdbmovieapp.interactors.movie_detail

import com.otaz.imdbmovieapp.domain.data.DataState
import com.otaz.imdbmovieapp.domain.model.TmdbMovieSpecs
import com.otaz.imdbmovieapp.network.TmdbApiService
import com.otaz.imdbmovieapp.network.model.TmdbMovieSpecsDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTmdbMovie(
    private val tmdbApiService: TmdbApiService,
    private val tmdbMovieSpecDtoMapper: TmdbMovieSpecsDtoMapper,
    ){
    fun execute(
        tmdb_apikey: String,
        id: Int,
    ): Flow<DataState<TmdbMovieSpecs>> = flow {
        try {
            emit(DataState.loading())

            val movieSpecs = getTmdbMovieSpecsFromNetwork(
                tmdb_apikey = tmdb_apikey,
                tmdb_id = id,
            )

            emit(DataState.success(movieSpecs))

        }catch (e: Exception){
            emit(DataState.error(e.message ?: "GetTmdbMovie: Unknown error"))
        }
    }

    // This can throw an exception if there is no network connection
    // This function gets Dto's from the network and converts them to MovieSpec Objects
    private suspend fun getTmdbMovieSpecsFromNetwork(
        tmdb_apikey: String,
        tmdb_id: Int,
    ): TmdbMovieSpecs{
        return tmdbMovieSpecDtoMapper.mapToDomainModel(
            tmdbApiService.getMovieSpecs(
                apikey = tmdb_apikey,
                id = tmdb_id,
            )
        )
    }
}