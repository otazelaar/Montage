package com.otaz.imdbmovieapp.presentation.movie

sealed class MovieEvent {

    data class GetMovieEvent(
        val id: String,
    ): MovieEvent()
}