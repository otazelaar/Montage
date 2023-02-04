package com.otaz.imdbmovieapp.network.model

import com.otaz.imdbmovieapp.domain.model.Movie
import com.otaz.imdbmovieapp.domain.util.DomainMapper

class MovieDtoMapper : DomainMapper<MovieDto, Movie> {

    override fun mapToDomainModel(model: MovieDto): Movie {
        return Movie(
            id = model.id,
            year = model.year,
            imageURL = model.imageURL,
            title = model.title,
            type = model.type,
        )
    }

    override fun mapFromDomainModel(domainModel: Movie): MovieDto {
        return MovieDto(
            id = domainModel.id,
            year = domainModel.year,
            imageURL = domainModel.imageURL,
            title = domainModel.title,
            type = domainModel.type,
        )
    }

    fun toDomainList(initial: List<MovieDto>): List<Movie>{
        return initial.map { mapToDomainModel(it) }
    }
}