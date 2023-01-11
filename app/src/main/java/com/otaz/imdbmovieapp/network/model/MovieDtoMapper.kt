package com.otaz.imdbmovieapp.network.model

import com.otaz.imdbmovieapp.domain.model.Movie
import com.otaz.imdbmovieapp.domain.util.DomainMapper

class MovieDtoMapper : DomainMapper<MovieDto, Movie> {

    override fun mapToDomainModel(model: MovieDto): Movie {
        return Movie(
            crew = model.crew,
            fullTitle = model.fullTitle,
            id = model.id,
            imDbRating = model.imDbRating,
            imDbRatingCount = model.imDbRatingCount,
            image = model.image,
            rank = model.rank,
            title = model.title,
            year = model.year,
        )
    }

    override fun mapFromDomainModel(domainModel: Movie): MovieDto {
        return MovieDto(
            crew = domainModel.crew,
            fullTitle = domainModel.fullTitle,
            id = domainModel.id,
            imDbRating = domainModel.imDbRating,
            imDbRatingCount = domainModel.imDbRatingCount,
            image = domainModel.image,
            rank = domainModel.rank,
            title = domainModel.title,
            year = domainModel.year,
        )
    }

    fun toDomainList(initial: List<MovieDto>): List<Movie>{
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Movie>): List<MovieDto>{
        return initial.map { mapFromDomainModel(it) }
    }

}