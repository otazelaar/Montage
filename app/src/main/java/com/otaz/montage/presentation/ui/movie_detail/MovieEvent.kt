package com.otaz.montage.presentation.ui.movie_detail

/**
 * This class is used to trigger events related to the MovieDetails
 */

sealed class MovieEvent {

    data class GetTmdbMovieEvent(
        val tmdb_id: Int,
    ): MovieEvent()

    object NextPageEvent: MovieEvent()

}