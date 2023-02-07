package com.otaz.imdbmovieapp.cache.model

import com.otaz.imdbmovieapp.domain.model.MovieSpecs
import com.otaz.imdbmovieapp.domain.util.DomainMapper

class MovieSpecEntityMapper : DomainMapper<MovieSpecsEntity, MovieSpecs> {
    override fun mapToDomainModel(model: MovieSpecsEntity): MovieSpecs {
        return MovieSpecs(
            id = model.id,
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
            imdbRating = model.imdbRating,
            imdbVotes = model.imdbVotes,
        )
    }

    override fun mapFromDomainModel(domainModel: MovieSpecs): MovieSpecsEntity {
        return MovieSpecsEntity(
            id = domainModel.id,
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
            imdbRating = domainModel.imdbRating,
            imdbVotes = domainModel.imdbVotes,
        )
    }

    fun fromEntityList(initial: List<MovieSpecsEntity>): List<MovieSpecs>{
        return initial.map { mapToDomainModel(it) }
    }

    fun toEntityList(initial: List<MovieSpecs>): List<MovieSpecsEntity>{
        return initial.map { mapFromDomainModel(it) }
    }
}