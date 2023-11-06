package com.otaz.montage.interactors.movie_list

import com.otaz.montage.cache.model.toMovie
import com.otaz.montage.domain.data.DataState
import com.otaz.montage.domain.model.Movie
import com.otaz.montage.domain.model.toMovieEntity
import com.otaz.montage.network.TmdbApiService
import com.otaz.montage.network.model.MovieDao
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

        // emit loading status
        emit(DataState.loading())

        // check for internet connection
        if (connectivityManager.isNetworkAvailable.value) {
            // if have internet, call network to have most up to date data
            runCatching {
                getMoviesFromNetwork(apikey, query, page)
            }.onSuccess { moviesFromNetwork ->
                // if network call is successful, emit result to VM
                emit(DataState.success(moviesFromNetwork))
            }.onFailure { error: Throwable ->
                // if network call is unsuccessful, try to handle errors here. Last resort, check db and emit from there
                emit(DataState.error(error.message.toString()))
                // if no available results cached, show an empty content screen
                // TODO(show empty content screen if no results)
            }
        } else if (!connectivityManager.isNetworkAvailable.value) {
            val moviesFromDB = movieDao.getMoviesByQuery(query, MOVIE_PAGINATION_PAGE_SIZE, page)
                .map { it.toMovie() }

            // if no internet, check DB and emit results from there
            if (moviesFromDB.isNotEmpty()) {
                emit(DataState.success(moviesFromDB))
            }
        }
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

//
//            // get movies from DB
//            val moviesFromDB = movieDao.getMoviesByQuery(query, MOVIE_PAGINATION_PAGE_SIZE, page).map { it.toMovie() }
//
//            // check if movies are in DB, if yes then emit them
//            if (moviesFromDB.isNotEmpty()){
//                emit(DataState.success(moviesFromDB))
//            }else {
//                // If movies are not present in DB, make network call, emit moviesFromNetwork, then store moviesFromNetwork in DB
//                val moviesFromNetwork = getMoviesFromNetwork(apikey, query, page)
//                emit(DataState.success(moviesFromNetwork))
//                movieDao.insertMovies(moviesFromNetwork.map { it.toMovieEntity() })
//            }
//        }catch(e: Exception){
//            emit(DataState.error(e.message?: "Unknown Error"))
//
//        }


// emit loading status
// check for internet connection
// if have internet, call network to have most up to date data
// if network call is successful, emit result to VM
// if unsuccessful, check DB and emit available results from there
// if no available results cached, show an empty content screen
// if no internet, check DB and emit results from there

// in the above code, try to consolidate the checking of the DB being full into one separate function


//        val statusResult = runCatching {
//            getMoviesFromNetwork(apikey, query, page)
//        }.onSuccess { moviesFromNetwork ->
//            // Insert list of movies into DB
//            movieDao.insertMovies(moviesFromNetwork.map { it.toMovieEntity() })
//            emit(DataState.success(moviesFromNetwork))
//        }.onFailure { error: Throwable ->
//            emit(DataState.error(error.message.toString()))
//            println("Go network error: ${error.message}")
//        }
//        val moviesFromDB = movieDao.getMoviesByQuery(query)
//        Log.d(TAG, "moviesFromDB: $moviesFromDB")
//        println("StatusResult is: $statusResult")