package com.otaz.imdbmovieapp.network.model

import com.otaz.imdbmovieapp.domain.model.Configurations
import com.otaz.imdbmovieapp.domain.util.DomainMapper

class ConfigurationDtoMapper : DomainMapper<ImageConfigurationsDto, Configurations> {

    override fun mapToDomainModel(model: ImageConfigurationsDto): Configurations {
        return Configurations(
            backdrop_sizes = model.backdrop_sizes,
            base_url = model.base_url,
            logo_sizes = model.logo_sizes,
            poster_sizes = model.poster_sizes,
            profile_sizes = model.profile_sizes,
            secure_base_url = model.secure_base_url,
            still_sizes = model.still_sizes,
        )
    }

    override fun mapFromDomainModel(domainModel: Configurations): ImageConfigurationsDto {
        return ImageConfigurationsDto(
            backdrop_sizes = domainModel.backdrop_sizes,
            base_url = domainModel.base_url,
            logo_sizes = domainModel.logo_sizes,
            poster_sizes = domainModel.poster_sizes,
            profile_sizes = domainModel.profile_sizes,
            secure_base_url = domainModel.secure_base_url,
            still_sizes = domainModel.still_sizes,
        )
    }

    fun toDomainList(initial: List<ImageConfigurationsDto>): List<Configurations>{
        return initial.map { mapToDomainModel(it) }
    }
}