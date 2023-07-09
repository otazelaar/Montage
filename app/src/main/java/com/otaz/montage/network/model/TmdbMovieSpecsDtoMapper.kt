package com.otaz.montage.network.model

import com.otaz.montage.domain.model.TmdbMovieSpecs
import com.otaz.montage.domain.util.DomainMapper

class TmdbMovieSpecsDtoMapper : DomainMapper<TmdbMovieSpecsDto, TmdbMovieSpecs> {

    override fun mapToDomainModel(model: TmdbMovieSpecsDto): TmdbMovieSpecs {
        return TmdbMovieSpecs(
            backdrop_path = model.backdrop_path,
            id = model.id,
            imdb_id = model.imdb_id,
            overview = model.overview,
            title = model.title,
        )
    }

    override fun mapFromDomainModel(domainModel: TmdbMovieSpecs): TmdbMovieSpecsDto {
        return TmdbMovieSpecsDto(
            backdrop_path = domainModel.backdrop_path,
            id = domainModel.id,
            imdb_id = domainModel.imdb_id,
            overview = domainModel.overview,
            title = domainModel.title,
        )
    }

    fun toDomainList(initial: List<TmdbMovieSpecsDto>): List<TmdbMovieSpecs>{
        return initial.map { mapToDomainModel(it) }
    }
}