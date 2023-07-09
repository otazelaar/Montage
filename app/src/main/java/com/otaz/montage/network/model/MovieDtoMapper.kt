package com.otaz.montage.network.model

import com.otaz.montage.domain.model.Movie
import com.otaz.montage.domain.util.DomainMapper

class MovieDtoMapper : DomainMapper<MovieDto, Movie> {

    override fun mapToDomainModel(model: MovieDto): Movie {
        return Movie(
            id = model.id,
            adult = model.adult,
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