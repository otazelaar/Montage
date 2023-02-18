package com.otaz.imdbmovieapp.interactors.movie

import com.otaz.imdbmovieapp.domain.data.DataState
import com.otaz.imdbmovieapp.domain.model.MovieSpecs
import com.otaz.imdbmovieapp.network.MovieService
import com.otaz.imdbmovieapp.network.model.MovieSpecsDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMovie(
    private val movieService: MovieService,
    private val movieSpecDtoMapper: MovieSpecsDtoMapper,
){
    fun execute(
        apikey: String,
        id: Int,
    ): Flow<DataState<MovieSpecs>> = flow {
        try {
            emit(DataState.loading())

            val movieSpecs = getMovieSpecsFromNetwork(
                apikey = apikey,
                id = id,
            )

            emit(DataState.success(movieSpecs))

        }catch (e: Exception){
            emit(DataState.error(e.message ?: "GetMovie: Unknown error"))
        }
    }

    // This can throw an exception if there is no network connection
    // This function gets Dto's from the network and converts them to MovieSpec Objects
    private suspend fun getMovieSpecsFromNetwork(
        apikey: String,
        id: Int,
    ): MovieSpecs{
        return movieSpecDtoMapper.mapToDomainModel(
            movieService.getMovie(
                apikey = apikey,
                id = id,
            )
        )
    }
}