package com.otaz.imdbmovieapp.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity matching the API call AdvancedSearch
 */

@Entity(tableName = "movies")
data class MovieEntity(

    // Value from API Ex: "tt0772174"
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: String,

    // Value from API
    @ColumnInfo(name = "imDbRating")
    var imdbRating: String?,

    // Value from API
    @ColumnInfo(name = "image")
    var imageURL: String,

    // Value from API
    @ColumnInfo(name = "title")
    var title: String,

    // Value from API
    @ColumnInfo(name = "plot")
    var description: String?,

//    /**
//     * Value from API
//     * Comma separated list of keywords
//     * EX: "billionaire, inventor, robot suit,"
//     */
//    @ColumnInfo(name = "keywordList")
//    var keywords: String = "",
)