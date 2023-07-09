package com.otaz.montage.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "adult")
    var adult: Boolean,

    @ColumnInfo(name = "overview")
    var overview: String,

    @ColumnInfo(name = "posterPath")
    var poster_path: String?,

    @ColumnInfo(name = "releaseDate")
    var release_date: String?,

    @ColumnInfo(name = "title")
    var title: String,
)