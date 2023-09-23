//package com.otaz.montage.network.model
//
//import com.otaz.montage.domain.model.MovieReview
//
//class MovieReviewsDtoMapper : DomainMapper<MovieReviewDto, MovieReview> {
//
//    override fun mapToDomainModel(model: MovieReviewDto): MovieReview {
//        return MovieReview(
//            author = model.author,
//            content = model.content,
//        )
//    }
//
//    override fun mapFromDomainModel(domainModel: MovieReview): MovieReviewDto {
//        return MovieReviewDto(
//            author = domainModel.author,
//            content = domainModel.content,
//            )
//    }
//
//    fun toDomainList(initial: List<MovieReviewDto>): List<MovieReview>{
//        return initial.map { mapToDomainModel(it) }
//    }
//}