package com.otaz.montage.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.otaz.montage.domain.model.Movie

@Entity(tableName = "movies")
data class MovieEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "adult")
    var adult: Boolean,

    @ColumnInfo(name = "backdropPath")
    var backdrop_path: String?,

    @ColumnInfo(name = "overview")
    var overview: String,

    @ColumnInfo(name = "posterPath")
    var poster_path: String?,

    @ColumnInfo(name = "releaseDate")
    var release_date: String?,

    @ColumnInfo(name = "title")
    var title: String,
)

fun MovieEntity.toMovie(): Movie {
    return Movie(
        id = id,
        adult = adult,
        backdrop_path = backdrop_path,
        overview = overview,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
    )
}

fun entitiesToMovie(movieEntities: List<MovieEntity>): List<Movie> {
    return movieEntities.map { it.toMovie() }
}
