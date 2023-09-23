//package com.otaz.montage.network.model
//
//import com.otaz.montage.domain.model.ImageConfigs
//
//class ConfigsDtoMapper : DomainMapper<ImageConfigsDto, ImageConfigs> {
//
//    override fun mapToDomainModel(model: ImageConfigsDto): ImageConfigs {
//        return ImageConfigs(
//            backdrop_sizes = model.backdrop_sizes,
//            base_url = model.base_url,
//            logo_sizes = model.logo_sizes,
//            poster_sizes = model.poster_sizes,
//            profile_sizes = model.profile_sizes,
//            secure_base_url = model.secure_base_url,
//            still_sizes = model.still_sizes,
//        )
//    }
//
//    override fun mapFromDomainModel(domainModel: ImageConfigs): ImageConfigsDto {
//        return ImageConfigsDto(
//            backdrop_sizes = domainModel.backdrop_sizes,
//            base_url = domainModel.base_url,
//            logo_sizes = domainModel.logo_sizes,
//            poster_sizes = domainModel.poster_sizes,
//            profile_sizes = domainModel.profile_sizes,
//            secure_base_url = domainModel.secure_base_url,
//            still_sizes = domainModel.still_sizes,
//        )
//    }
//
//    fun toDomainList(initial: List<ImageConfigsDto>): List<ImageConfigs>{
//        return initial.map { mapToDomainModel(it) }
//    }
//}