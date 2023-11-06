package com.otaz.montage.network.model

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


    @Query("DELETE FROM movies WHERE id = :primaryKey")
    suspend fun deleteMovie(primaryKey: String): Int

    // Same as 'searchMovies' function, but no query.
    @Query(" SELECT * FROM movies ")
    suspend fun getAllMovies(): List<MovieEntity>

//    @Query("SELECT * FROM movies WHERE id = :id")
//    suspend fun getMovieById(id: String): MovieEntity?

//    @Query("DELETE FROM movies WHERE id IN (:ids)")
//    suspend fun deleteMovies(ids: List<String>): Int

//    @Query("DELETE FROM movies")
//    suspend fun deleteAllMovies()
}