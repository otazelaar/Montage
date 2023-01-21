package com.otaz.imdbmovieapp.repository

import com.otaz.imdbmovieapp.domain.model.Movie
import com.otaz.imdbmovieapp.domain.model.Poster
import com.otaz.imdbmovieapp.network.MovieService
import com.otaz.imdbmovieapp.network.model.MovieDtoMapper
import com.otaz.imdbmovieapp.network.model.PosterDtoMapper

class PosterRepositoryImpl(
    private val movieService: MovieService,
    private val mapper: PosterDtoMapper,
): PosterRepository {
    override suspend fun poster(apikey: String, id: String): List<Poster> {
        val result = movieService.poster(apikey = apikey, id = id).posters
        return mapper.toDomainList(result)
    }
}