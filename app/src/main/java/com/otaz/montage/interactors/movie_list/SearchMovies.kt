package com.otaz.montage.interactors.movie_list

import com.otaz.montage.cache.model.toMovie
import com.otaz.montage.domain.data.DataState
import com.otaz.montage.domain.model.Movie
import com.otaz.montage.network.TmdbApiService
import com.otaz.montage.cache.MovieDao
import com.otaz.montage.domain.model.toMovieEntity
import com.otaz.montage.network.model.toMovie
import com.otaz.montage.presentation.ConnectivityManager
import com.otaz.montage.util.MOVIE_PAGINATION_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchMovies(
    private val tmdbApiService: TmdbApiService,
    private val movieDao: MovieDao,
) {
    fun execute(
        connectivityManager: ConnectivityManager,
        apikey: String,
        query: String,
        page: Int,
    ): Flow<DataState<List<Movie>>> = flow {

        val status = connectivityManager.isNetworkAvailable.value
        // emit loading status
        emit(DataState.loading())

        // check for internet connection
        if (status == true) {
            // if have internet, call network to have most up to date data
            runCatching {
                getMoviesFromNetwork(apikey, query, page)
            }.onSuccess { moviesFromNetwork ->
                // convert successful result from Movie to MovieEntity
                val newMoviesToBeCached = moviesFromNetwork.map { it.toMovieEntity() }
                // cache movies in DB
                movieDao.insertMovies(newMoviesToBeCached)

            }.onFailure { error: Throwable ->
                // if network call is unsuccessful, try to handle errors here. Last resort, check db and emit from there
                emit(DataState.error(error.message.toString()))
                // if no available results cached, show an empty content screen
                // TODO(show empty content screen if no results)
            }
        }

        // get list of Movie from DB using DAO getMoviesByQuery function as modeled after API call
        val moviesFromDB = movieDao.getMoviesByQuery(query, MOVIE_PAGINATION_PAGE_SIZE, page)
            .map { it.toMovie() }
        // emit list of Movie from DB as SST
        emit(DataState.success(moviesFromDB))

//        // stop observing internet connection here so you don't observe forever
//        connectivityManager.unregisterConnectionObserver()
    }

    private suspend fun getMoviesFromNetwork(
        apikey: String,
        query: String,
        page: Int,
    ): List<Movie> {
        return tmdbApiService.searchMovies(
            apikey = apikey,
            query = query,
            page = page,
        ).moviesDto.map { it.toMovie() }.filter {
            it.poster_path != null &&
            it.backdrop_path != null &&
            !it.adult
        }
    }
}