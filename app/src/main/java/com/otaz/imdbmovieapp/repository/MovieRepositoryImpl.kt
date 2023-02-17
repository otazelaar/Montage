package com.otaz.imdbmovieapp.repository

import com.otaz.imdbmovieapp.domain.model.Movie
import com.otaz.imdbmovieapp.network.MovieService
import com.otaz.imdbmovieapp.network.model.MovieDtoMapper

class MovieRepositoryImpl(
    private val movieService: MovieService,
    private val mapper: MovieDtoMapper,
): MovieRepository {
    override suspend fun search(apikey: String, query: String): List<Movie> {
        val result = movieService.search(apikey = apikey, query = query).movies
        return mapper.toDomainList(result)
    }
}