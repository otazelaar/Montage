package com.otaz.imdbmovieapp.interactors.movie_detail

import com.otaz.imdbmovieapp.domain.data.DataState
import com.otaz.imdbmovieapp.domain.model.OmdbMovieSpecs
import com.otaz.imdbmovieapp.network.OmdbApiService
import com.otaz.imdbmovieapp.network.model.OmdbMoviesSpecsDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetOmdbMovie(
    private val omdbApiService: OmdbApiService,
    private val omdbMovieSpecDtoMapper: OmdbMoviesSpecsDtoMapper,
){
    fun execute(
        omdb_apikey: String,
        omdb_id: String,
    ): Flow<DataState<OmdbMovieSpecs>> = flow {
        try {
            emit(DataState.loading())

            val omdbMovieSpecs = getOmdbMovieSpecsFromNetwork(
                omdb_apikey = omdb_apikey,
                omdb_id = omdb_id,
            )

            emit(DataState.success(omdbMovieSpecs))

        }catch (e: Exception){
            emit(DataState.error(e.message ?: "GetOmdbMovie: Unknown error"))
        }
    }

    // This can throw an exception if there is no network connection
    // This function gets Dto's from the network and converts them to MovieSpec Objects
    private suspend fun getOmdbMovieSpecsFromNetwork(
        omdb_apikey: String,
        omdb_id: String,
    ): OmdbMovieSpecs {
        return omdbMovieSpecDtoMapper.mapToDomainModel(
            omdbApiService.get(
                apikey = omdb_apikey,
                id = omdb_id,
            )
        )
    }
}