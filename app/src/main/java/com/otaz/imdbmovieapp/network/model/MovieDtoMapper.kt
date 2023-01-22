package com.otaz.imdbmovieapp.network.model

import com.otaz.imdbmovieapp.domain.model.Movie
import com.otaz.imdbmovieapp.domain.util.DomainMapper

class MovieDtoMapper : DomainMapper<MovieDto, Movie> {

    override fun mapToDomainModel(model: MovieDto): Movie {
        return Movie(
            id = model.id,
            imdbRating = model.imdbRating,
            imageURL = model.imageURL,
            title = model.title,
            description = model.description,
        )
    }

    override fun mapFromDomainModel(domainModel: Movie): MovieDto {
        return MovieDto(
            id = domainModel.id,
            imdbRating = domainModel.imdbRating,
            imageURL = domainModel.imageURL,
            title = domainModel.title,
            description = domainModel.description,
        )
    }

    fun toDomainList(initial: List<MovieDto>): List<Movie>{
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Movie>): List<MovieDto>{
        return initial.map { mapFromDomainModel(it) }
    }

}