package com.otaz.imdbmovieapp.network.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.otaz.imdbmovieapp.cache.model.MovieSpecsEntity
import com.otaz.imdbmovieapp.util.MOVIE_PAGINATION_PAGE_SIZE

/**
 * The Long value returned by insertMovie represents whether or not the insert was successful.
 * If successful, it will return the row number in the database. If unsuccessful, it will return the integer -1.
 */

@Dao
interface MovieSpecDao {

    @Insert
    suspend fun insertMovie(movie: MovieSpecsEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<MovieSpecsEntity>): LongArray

    @Query("SELECT * FROM movie_specs WHERE id = :id")
    suspend fun getMovieById(id: String): MovieSpecsEntity

    @Query("DELETE FROM movie_specs WHERE id IN (:ids)")
    suspend fun deleteMovies(ids: List<String>): Int

    @Query("DELETE FROM movie_specs")
    suspend fun deleteAllMovies()

    @Query("DELETE FROM movie_specs WHERE id = :primaryKey")
    suspend fun deleteMovie(primaryKey: String): Int

    /**
     * Retrieve movies for a particular page.
     * Ex: page = 2 retrieves movies from 30-60.
     * Ex: page = 3 retrieves movies from 60-90
     */
    @Query("""
        SELECT * FROM movie_specs 
        WHERE title LIKE '%' || :query || '%'
        ORDER BY title DESC LIMIT :pageSize OFFSET ((:page - 1) * :pageSize)
        """)
    suspend fun searchMovies(
        query: String,
        page: Int,
        pageSize: Int = MOVIE_PAGINATION_PAGE_SIZE
    ): List<MovieSpecsEntity>

    /**
     * Same as 'searchMovies' function, but no query.
     */
    @Query("""
        SELECT * FROM movie_specs 
        ORDER BY title DESC LIMIT :pageSize OFFSET ((:page - 1) * :pageSize)
    """)
    suspend fun getAllMovies(
        page: Int,
        pageSize: Int = MOVIE_PAGINATION_PAGE_SIZE
    ): List<MovieSpecsEntity>

    /**
     * Restore Movies after process death
     */
    @Query("""
        SELECT * FROM movie_specs 
        WHERE title LIKE '%' || :query || '%'
        ORDER BY title DESC LIMIT (:page * :pageSize)
        """)
    suspend fun restoreMovies(
        query: String,
        page: Int,
        pageSize: Int = MOVIE_PAGINATION_PAGE_SIZE
    ): List<MovieSpecsEntity>

    /**
     * Same as 'restoreMovies' function, but no query.
     */
    @Query("""
        SELECT * FROM movie_specs 
        ORDER BY title DESC LIMIT (:page * :pageSize)
    """)
    suspend fun restoreAllMovies(
        page: Int,
        pageSize: Int = MOVIE_PAGINATION_PAGE_SIZE
    ): List<MovieSpecsEntity>
}