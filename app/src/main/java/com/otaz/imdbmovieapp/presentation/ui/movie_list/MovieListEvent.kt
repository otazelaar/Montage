package com.otaz.imdbmovieapp.presentation.ui.movie_list

import com.otaz.imdbmovieapp.domain.model.Movie

/**
 * This class is used to trigger events related to the MovieList
 */

sealed class MovieListEvent {
    object NewSearchEvent: MovieListEvent()
    object NextPageEvent: MovieListEvent()
    data class SaveMovieEvent(val movie: Movie): MovieListEvent()
}