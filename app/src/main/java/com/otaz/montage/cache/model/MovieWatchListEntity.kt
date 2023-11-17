//package com.otaz.montage.cache.model
//
//import androidx.room.ColumnInfo
//import androidx.room.Entity
//import androidx.room.PrimaryKey
//import com.otaz.montage.domain.model.Movie
//
//@Entity(tableName = "movies_watchlist")
//data class MovieWatchListEntity(
//
//    @PrimaryKey(autoGenerate = false)
//    @ColumnInfo(name = "id")
//    var id: Int,
//
//    @ColumnInfo(name = "adult")
//    var adult: Boolean,
//
//    @ColumnInfo(name = "backdropPath")
//    var backdrop_path: String?,
//
//    @ColumnInfo(name = "overview")
//    var overview: String,
//
//    @ColumnInfo(name = "posterPath")
//    var poster_path: String?,
//
//    @ColumnInfo(name = "releaseDate")
//    var release_date: String?,
//
//    @ColumnInfo(name = "title")
//    var title: String,
//
//    @ColumnInfo(name = "orderAdded")
//    var orderAdded: Int,
//)
//
//fun MovieWatchListEntity.toMovie(): Movie {
//    return Movie(
//        id = id,
//        adult = adult,
//        backdrop_path = backdrop_path,
//        overview = overview,
//        poster_path = poster_path,
//        release_date = release_date,
//        title = title,
//        orderAdded = orderAdded,
//    )
//}
//
//fun entitiesToMovie(movieEntities: List<MovieWatchListEntity>): List<Movie> {
//    return movieEntities.map { it.toMovie() }
//}