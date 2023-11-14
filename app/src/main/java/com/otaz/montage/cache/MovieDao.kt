package com.otaz.montage.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.otaz.montage.cache.model.CounterEntity
import com.otaz.montage.cache.model.MovieEntity
import com.otaz.montage.cache.model.MovieWatchListEntity

/**
 * The Long value returned by insertMovie represents whether or not the insert was successful.
 * If successful, it will return the row number in the database. If unsuccessful, it will return the integer -1.
 */

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: MovieEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<MovieEntity>): LongArray

    // DB version of the SearchMovies API call
    // pageSize is 4
    @Query("""
        SELECT * FROM movies 
        WHERE title LIKE '%' || :query || '%'
        LIMIT :paginationSize OFFSET ((:page -1) * :paginationSize)
        """)
    suspend fun getMoviesByQuery(
        query: String,
        paginationSize: Int,
        page: Int,
    ): List<MovieEntity>

    // Same as 'searchMovies' function, but no query.
    @Query(" SELECT * FROM movies ")
    suspend fun getAllMovies(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieWatchList(movie: MovieWatchListEntity): Long

    @Query("DELETE FROM movies_watchlist WHERE id = :primaryKey")
    suspend fun deleteMovie(primaryKey: String): Int

    //order in most recently added
    // TODO: Instead of having a movie_list table, just pull from the movies table.
    // select from movies where "isInWatchList" = true
    @Query(" SELECT * FROM movies_watchlist ORDER BY orderAdded DESC")
    suspend fun getWatchList(): List<MovieWatchListEntity>

    // Counter to save the up to date order for watch list to survive closing the app
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCounter(counter: CounterEntity): Long

    @Query(" SELECT * FROM counter ")
    suspend fun getCounterValue(): CounterEntity


//    @Query("SELECT * FROM movies WHERE id = :id")
//    suspend fun getMovieById(id: String): MovieEntity?

//    @Query("DELETE FROM movies WHERE id IN (:ids)")
//    suspend fun deleteMovies(ids: List<String>): Int

//    @Query("DELETE FROM movies")
//    suspend fun deleteAllMovies()
}