package com.otaz.montage.network.model

import com.otaz.montage.domain.model.OmdbMovieSpecs
import com.otaz.montage.domain.util.DomainMapper

class OmdbMoviesSpecsDtoMapper : DomainMapper<OmdbMovieSpecDto, OmdbMovieSpecs> {

    override fun mapToDomainModel(model: OmdbMovieSpecDto): OmdbMovieSpecs {
        return OmdbMovieSpecs(
            director = model.director,
            metascore = model.metascore,
            released = model.released,
            runtime = model.runtime,
            year = model.year,
            id = model.imdbID,
            imdbRating = model.imdbRating,
        )
    }

    override fun mapFromDomainModel(domainModel: OmdbMovieSpecs): OmdbMovieSpecDto {
        return OmdbMovieSpecDto(
            director = domainModel.director,
            metascore = domainModel.metascore,
            released = domainModel.released,
            runtime = domainModel.runtime,
            year = domainModel.year,
            imdbID = domainModel.id,
            imdbRating = domainModel.imdbRating,
        )
    }

    fun toDomainList(initial: List<OmdbMovieSpecDto>): List<OmdbMovieSpecs>{
        return initial.map { mapToDomainModel(it) }
    }
}