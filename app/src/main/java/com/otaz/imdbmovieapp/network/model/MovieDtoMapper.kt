package com.otaz.imdbmovieapp.network.model

import com.otaz.imdbmovieapp.domain.model.Movie
import com.otaz.imdbmovieapp.domain.util.DomainMapper

class MovieDtoMapper : DomainMapper<MovieDto, Movie> {

    override fun mapToDomainModel(model: MovieDto): Movie {
        return Movie(
            id = model.id,
            adult = model.adult,
            backdrop_path = model.backdrop_path,
            overview = model.overview,
            poster_path = model.poster_path,
            release_date = model.release_date,
            title = model.title,
        )
    }

    override fun mapFromDomainModel(domainModel: Movie): MovieDto {
        return MovieDto(
            id = domainModel.id,
            adult = domainModel.adult,
            backdrop_path = domainModel.backdrop_path,
            overview = domainModel.overview,
            poster_path = domainModel.poster_path,
            release_date = domainModel.release_date,
            title = domainModel.title,
        )
    }

    fun toDomainList(initial: List<MovieDto>): List<Movie>{
        return initial.map { mapToDomainModel(it) }
    }
}