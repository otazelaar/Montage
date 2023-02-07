package com.otaz.imdbmovieapp.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity matching the API call for movie specs
 */

@Entity(tableName = "movie_specs")
data class MovieSpecsEntity(

    // Value from API Ex: "tt0772174"
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "actors")
    val actors: String,
    @ColumnInfo(name = "awards")
    val awards: String,
    @ColumnInfo(name = "boxOffice")
    val boxOffice: String,
    @ColumnInfo(name = "country")
    val country: String,
    @ColumnInfo(name = "dvd")
    val dvd: String,
    @ColumnInfo(name = "director")
    val director: String,
    @ColumnInfo(name = "genre")
    val genre: String,
    @ColumnInfo(name = "language")
    val language: String,
    @ColumnInfo(name = "metascore")
    val metascore: String,
    @ColumnInfo(name = "plot")
    val plot: String,
    @ColumnInfo(name = "poster")
    val poster: String,
    @ColumnInfo(name = "production")
    val production: String,
    @ColumnInfo(name = "rated")
    val rated: String,
    @ColumnInfo(name = "released")
    val released: String,
    @ColumnInfo(name = "response")
    val response: String,
    @ColumnInfo(name = "runtime")
    val runtime: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "website")
    val website: String,
    @ColumnInfo(name = "writer")
    val writer: String,
    @ColumnInfo(name = "year")
    val year: String,
    @ColumnInfo(name = "imdbRating")
    val imdbRating: String,
    @ColumnInfo(name = "imdbVotes")
    val imdbVotes: String
)