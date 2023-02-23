package com.otaz.imdbmovieapp.presentation.ui.movie

sealed class MovieEvent {

    data class GetTmdbMovieEvent(
        val tmdb_id: Int,
    ): MovieEvent()

    data class GetOmdbMovieEvent(
        val omdb_id: String,
    ): MovieEvent()

//    object GetOmdbMovieEvent: MovieEvent()

}