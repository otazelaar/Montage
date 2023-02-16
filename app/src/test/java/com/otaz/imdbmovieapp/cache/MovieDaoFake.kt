package com.otaz.imdbmovieapp.cache

import com.otaz.imdbmovieapp.cache.model.MovieEntity
import com.otaz.imdbmovieapp.network.model.MovieDao

class MovieDaoFake(
    private val appDatabaseFake: AppDatabaseFake
) : MovieDao {
    override suspend fun insertMovie(movie: MovieEntity): Long {
        appDatabaseFake.movies.add(movie)
        // return success
        return 1
    }

    override suspend fun insertMovies(movies: List<MovieEntity>): LongArray {
        appDatabaseFake.movies.addAll(movies)
        // return success
        return longArrayOf(1)
    }

    override suspend fun getMovieById(id: String): MovieEntity? {
        return appDatabaseFake.movies.find { it.id == id }
    }

    override suspend fun deleteMovies(ids: List<String>): Int {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllMovies() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMovie(primaryKey: String): Int {
        TODO("Not yet implemented")
    }

    override suspend fun searchMovies(query: String, page: Int, pageSize: Int): List<MovieEntity> {
        return appDatabaseFake.movies
    }

    override suspend fun getAllMovies(page: Int, pageSize: Int): List<MovieEntity> {
        return appDatabaseFake.movies
    }

    override suspend fun restoreMovies(query: String, page: Int, pageSize: Int): List<MovieEntity> {
        return appDatabaseFake.movies
    }

    override suspend fun restoreAllMovies(page: Int, pageSize: Int): List<MovieEntity> {
        return appDatabaseFake.movies
    }

}