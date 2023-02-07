package com.otaz.imdbmovieapp.network.model

import com.otaz.imdbmovieapp.domain.model.MovieSpecs
import com.otaz.imdbmovieapp.domain.util.DomainMapper

class MovieSpecDtoMapper : DomainMapper<MovieSpecDto, MovieSpecs> {

    override fun mapToDomainModel(model: MovieSpecDto): MovieSpecs {
        return MovieSpecs(
            actors = model.actors,
            awards = model.awards,
            boxOffice = model.boxOffice,
            country = model.country,
            dvd = model.dvd,
            director = model.director,
            genre = model.genre,
            language = model.language,
            metascore = model.metascore,
            plot = model.plot,
            poster = model.poster,
            production = model.production,
            rated = model.rated,
            released = model.released,
            response = model.response,
            runtime = model.runtime,
            title = model.title,
            type = model.type,
            website = model.website,
            writer = model.writer,
            year = model.year,
            id = model.imdbID,
            imdbRating = model.imdbRating,
            imdbVotes = model.imdbVotes,
        )
    }

    override fun mapFromDomainModel(domainModel: MovieSpecs): MovieSpecDto {
        return MovieSpecDto(
            actors = domainModel.actors,
            awards = domainModel.awards,
            boxOffice = domainModel.boxOffice,
            country = domainModel.country,
            dvd = domainModel.dvd,
            director = domainModel.director,
            genre = domainModel.genre,
            language = domainModel.language,
            metascore = domainModel.metascore,
            plot = domainModel.plot,
            poster = domainModel.poster,
            production = domainModel.production,
            rated = domainModel.rated,
            released = domainModel.released,
            response = domainModel.response,
            runtime = domainModel.runtime,
            title = domainModel.title,
            type = domainModel.type,
            website = domainModel.website,
            writer = domainModel.writer,
            year = domainModel.year,
            imdbID = domainModel.id,
            imdbRating = domainModel.imdbRating,
            imdbVotes = domainModel.imdbVotes,
        )
    }

    fun toDomainList(initial: List<MovieSpecDto>): List<MovieSpecs>{
        return initial.map { mapToDomainModel(it) }
    }
}