package com.otaz.imdbmovieapp.repository

import com.otaz.imdbmovieapp.domain.model.Movie
import com.otaz.imdbmovieapp.network.MovieService
import com.otaz.imdbmovieapp.network.model.MovieDtoMapper

class MovieRepositoryImpl(
    private val movieService: MovieService,
    private val mapper: MovieDtoMapper,
): MovieRepository {
    override suspend fun search(apikey: String, query: String, page: String): List<Movie> {
        val result = movieService.search(apikey = apikey, query = query, page = page).movies
        return mapper.toDomainList(result)
    }
}