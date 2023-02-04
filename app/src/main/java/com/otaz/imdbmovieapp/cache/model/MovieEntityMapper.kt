package com.otaz.imdbmovieapp.cache.model

import com.otaz.imdbmovieapp.domain.model.Movie
import com.otaz.imdbmovieapp.domain.util.DomainMapper

class MovieEntityMapper : DomainMapper<MovieEntity, Movie> {
    override fun mapToDomainModel(model: MovieEntity): Movie {
        return Movie(
            id = model.id,
            year = model.imdbRating,
            imageURL = model.imageURL,
            title = model.title,
            type = model.description,
//            keywords = convertKeywordsToList(model.keywords),
        )
    }

    override fun mapFromDomainModel(domainModel: Movie): MovieEntity {
        return MovieEntity(
            id = domainModel.id,
            imdbRating = domainModel.year,
            imageURL = domainModel.imageURL,
            title = domainModel.title,
            description = domainModel.type,
//            keywords = convertKeywordListToString(domainModel.keywords)
        )
    }

    private fun convertKeywordListToString(keywords: List<String>): String {
        val keywordsString = StringBuilder()
        for(keyword in keywords){
            keywordsString.append("$keyword,")
        }
        return keywordsString.toString()
    }

    private fun convertKeywordsToList(keywordsString: String?): List<String> {
        val list: ArrayList<String> = ArrayList()
        keywordsString?.let {
            for(keyword in it.split(",")){
                list.add(keyword)
            }
        }
        return list
    }

    fun fromEntityList(initial: List<MovieEntity>): List<Movie>{
        return initial.map { mapToDomainModel(it) }
    }

    fun toEntityList(initial: List<Movie>): List<MovieEntity>{
        return initial.map { mapFromDomainModel(it) }
    }
}