package com.otaz.imdbmovieapp.cache

import com.otaz.imdbmovieapp.cache.model.MovieSpecsEntity

class MovieSpecDaoFake(
    private val appDatabaseFake: AppDatabaseFake
): MovieSpecDao{
    override suspend fun insertMovie(movie: MovieSpecsEntity): Long {
        appDatabaseFake.movieSpecs.add(movie)
        // return success
        return 1
    }

    override suspend fun insertMovies(movies: List<MovieSpecsEntity>): LongArray {
        appDatabaseFake.movieSpecs.addAll(movies)
        // return success
        return longArrayOf(1)
    }

    override suspend fun getMovieById(id: String): MovieSpecsEntity? {
        return appDatabaseFake.movieSpecs.find { it.id == id }
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

    override suspend fun searchMovies(
        query: String,
        page: Int,
        pageSize: Int
    ): List<MovieSpecsEntity> {
        return appDatabaseFake.movieSpecs
    }

    override suspend fun getAllMovies(page: Int, pageSize: Int): List<MovieSpecsEntity> {
        return appDatabaseFake.movieSpecs
    }

    override suspend fun restoreMovies(
        query: String,
        page: Int,
        pageSize: Int
    ): List<MovieSpecsEntity> {
        return appDatabaseFake.movieSpecs
    }

    override suspend fun restoreAllMovies(page: Int, pageSize: Int): List<MovieSpecsEntity> {
        return appDatabaseFake.movieSpecs
    }

}