package com.otaz.montage.cache.model

import com.otaz.montage.domain.DomainMapper
import com.otaz.montage.domain.model.Movie

class MovieEntityMapper : DomainMapper<MovieEntity, Movie> {
    override fun mapToDomainModel(model: MovieEntity): Movie {
        return Movie(
            id = model.id,
            adult = model.adult,
            backdrop_path = model.backdrop_path,
            overview = model.overview,
            poster_path = model.poster_path,
            release_date = model.release_date,
            title = model.title,
        )
    }

    override fun mapFromDomainModel(domainModel: Movie): MovieEntity {
        return MovieEntity(
            id = domainModel.id,
            adult = domainModel.adult,
            backdrop_path = domainModel.backdrop_path,
            overview = domainModel.overview,
            poster_path = domainModel.poster_path,
            release_date = domainModel.release_date,
            title = domainModel.title,
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