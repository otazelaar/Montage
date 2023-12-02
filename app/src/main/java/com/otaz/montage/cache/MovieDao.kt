package com.otaz.montage.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.otaz.montage.cache.model.MovieEntity

/**
 * The Long value returned by insertMovie represents whether or not the insert was successful.
 * If successful, it will return the row number in the database. If unsuccessful, it will return the integer -1.
 */

@Dao
interface MovieDao {
    // TODO() this should probably be handled in the UC but adjust the date added for SaveMovie but not for CacheMovie
    // Replacing because this allows me to replace the old entity with a newly adjusted one and update the state
    // this way I can "remove" or add items to my watchlist
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: MovieEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(movies: List<MovieEntity>): LongArray

    // DB version of the SearchMovies API call
    // pageSize is 4
    @Query("""
        SELECT * FROM movies 
        WHERE title LIKE '%' || :query || '%'
        LIMIT :paginationSize OFFSET ((:page -1) * :paginationSize)
        """)
    fun getMoviesByQuery(
        query: String,
        paginationSize: Int,
        page: Int,
    ): List<MovieEntity>

    @Query(""" 
        SELECT * FROM movies 
        ORDER BY popularity DESC
        LIMIT :paginationSize OFFSET ((:page -1) * :paginationSize)
        """)
    fun getMostPopularMovies(
        paginationSize: Int,
        page: Int,
    ): List<MovieEntity>

    // change the following SQL to pull MovieEntity that only has a true boolean value for isInWatchlist
    // order results by date eventually
    // TODO() order by date
    @Query(" SELECT * FROM movies WHERE isInWatchlist = true ORDER BY timeSavedToWatchList DESC")
    fun getWatchListBoolean(): List<MovieEntity>

    @Query("DELETE FROM movies WHERE id = :primaryKey")
    fun deleteMovie(primaryKey: String): Int

//    @Query("SELECT * FROM movies WHERE id = :id")
//    suspend fun getMovieById(id: String): MovieEntity?

//    @Query("DELETE FROM movies WHERE id IN (:ids)")
//    suspend fun deleteMovies(ids: List<String>): Int

//    @Query("DELETE FROM movies")
//    suspend fun deleteAllMovies()
}