package com.otaz.imdbmovieapp.network.model

import com.otaz.imdbmovieapp.domain.model.Poster
import com.otaz.imdbmovieapp.domain.util.DomainMapper

class PosterDtoMapper : DomainMapper<PosterDto, Poster> {

    override fun mapToDomainModel(model: PosterDto): Poster {
        return Poster(
            id = model.id,
            link = model.link,
            aspectRatio = model.aspectRatio,
            language = model.language,
            width = model.width,
            height = model.height,
        )
    }

    override fun mapFromDomainModel(domainModel: Poster): PosterDto {
        return PosterDto(
            id = domainModel.id,
            link = domainModel.link,
            aspectRatio = domainModel.aspectRatio,
            language = domainModel.language,
            width = domainModel.width,
            height = domainModel.height,
        )
    }

    fun toDomainList(initial: List<PosterDto>): List<Poster> {
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Poster>): List<PosterDto> {
        return initial.map { mapFromDomainModel(it) }
    }
}

