package com.otaz.imdbmovieapp.presentation.ui.movie_detail

import com.otaz.imdbmovieapp.presentation.ui.movie_list.MovieListEvent

sealed class MovieEvent {

    data class GetTmdbMovieEvent(
        val tmdb_id: Int,
    ): MovieEvent()

    object NextPageEvent: MovieEvent()

// this did not work as it called the viewmodel over and overagain.
//    data class GetOmdbMovieEvent(
//        val omdb_id: String,
//    ): MovieEvent()

//    object GetOmdbMovieEvent: MovieEvent()

}