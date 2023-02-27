package com.otaz.imdbmovieapp.presentation.ui.movie_detail

sealed class MovieEvent {

    data class GetTmdbMovieEvent(
        val tmdb_id: Int,
    ): MovieEvent()

    object NextPageEvent: MovieEvent()

}