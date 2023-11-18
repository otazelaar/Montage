package com.otaz.montage.interactors.movie_list

import android.util.Log
import com.otaz.montage.cache.MovieDao
import com.otaz.montage.cache.model.toMovie
import com.otaz.montage.domain.data.DataState
import com.otaz.montage.domain.model.Movie
import com.otaz.montage.domain.model.toMovieEntity
import com.otaz.montage.network.TmdbApiService
import com.otaz.montage.network.model.toMovie
import com.otaz.montage.util.MOVIE_PAGINATION_PAGE_SIZE
import com.otaz.montage.util.TAG
import kotlinx.coroutines.flow.*

class GetMostPopularMovies(
    private val tmdbApiService: TmdbApiService,
    private val movieDao: MovieDao,
) {
    fun execute(
        isNetworkAvailable: Boolean,
        apikey: String,
        sortBy: String,
        page: Int,
    ): Flow<DataState<List<Movie>>> = flow {
        // emit loading status
        emit(DataState.loading())

        // check for internet connection
        if (isNetworkAvailable) {
            // if have internet, call network to have most up to date data
            runCatching {
                getMostPopularMoviesFromNetwork(apikey, sortBy, page)
            }.onSuccess { result ->
                // convert successful result from Movie to MovieEntity
                val newMoviesToBeCached = result.map { it.toMovieEntity() }

                Log.i(TAG, "getMostPopularMovies is working")
                // cache movies in DB
                movieDao.insertMovies(newMoviesToBeCached)
            }.onFailure { error: Throwable ->
                // if network call is unsuccessful, try to handle errors here. Last resort, check db and emit from there
                emit(DataState.error(error.message.toString()))
                println("Go network error: ${error.message}")
            }
        }

        // get list of Movie from DB using DAO getMoviesByQuery function as modeled after API call
        val moviesFromDB = movieDao.getMostPopularMovies(MOVIE_PAGINATION_PAGE_SIZE, page)
            .map { it.toMovie() }
        // emit list of Movie from DB as SST
        emit(DataState.success(moviesFromDB))

    }
    private suspend fun getMostPopularMoviesFromNetwork(
        apikey: String,
        sortBy: String,
        page: Int,
    ): List<Movie>{
        return tmdbApiService.getMostPopularMovies(
                apikey = apikey,
                sortBy = sortBy,
                page = page,
            ).moviesDto.map {
                it.toMovie()
        }.filter {
            it.poster_path != null &&
            it.backdrop_path != null &&
            !it.adult
        }
    }
}
