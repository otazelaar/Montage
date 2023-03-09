package com.otaz.imdbmovieapp.presentation.ui.movie_list

/**
 * This class is used to trigger events related to the MovieList
 */

sealed class MovieListEvent {
    object NewSearchEvent: MovieListEvent()
    object NextPageEvent: MovieListEvent()
}