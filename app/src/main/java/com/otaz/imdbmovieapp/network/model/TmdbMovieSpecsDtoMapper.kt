package com.otaz.imdbmovieapp.network.model

import com.otaz.imdbmovieapp.domain.model.TmdbMovieSpecs
import com.otaz.imdbmovieapp.domain.util.DomainMapper

class TmdbMovieSpecsDtoMapper : DomainMapper<TmdbMovieSpecsDto, TmdbMovieSpecs> {

    override fun mapToDomainModel(model: TmdbMovieSpecsDto): TmdbMovieSpecs {
        return TmdbMovieSpecs(
            adult = model.adult,
            backdrop_path = model.backdrop_path,
            budget = model.budget,
            homepage = model.homepage,
            id = model.id,
            imdb_id = model.imdb_id,
            original_language = model.original_language,
            original_title = model.original_title,
            overview = model.overview,
            popularity = model.popularity,
            poster_path = model.poster_path,
            release_date = model.release_date,
            revenue = model.revenue,
            runtime = model.runtime,
            status = model.status,
            tagline = model.tagline,
            title = model.title,
            video = model.video,
            vote_average = model.vote_average,
            vote_count = model.vote_count
        )
    }

    override fun mapFromDomainModel(domainModel: TmdbMovieSpecs): TmdbMovieSpecsDto {
        return TmdbMovieSpecsDto(
            adult = domainModel.adult,
            backdrop_path = domainModel.backdrop_path,
            budget = domainModel.budget,
            homepage = domainModel.homepage,
            id = domainModel.id,
            imdb_id = domainModel.imdb_id,
            original_language = domainModel.original_language,
            original_title = domainModel.original_title,
            overview = domainModel.overview,
            popularity = domainModel.popularity,
            poster_path = domainModel.poster_path,
            release_date = domainModel.release_date,
            revenue = domainModel.revenue,
            runtime = domainModel.runtime,
            status = domainModel.status,
            tagline = domainModel.tagline,
            title = domainModel.title,
            video = domainModel.video,
            vote_average = domainModel.vote_average,
            vote_count = domainModel.vote_count
        )
    }

    fun toDomainList(initial: List<TmdbMovieSpecsDto>): List<TmdbMovieSpecs>{
        return initial.map { mapToDomainModel(it) }
    }
}