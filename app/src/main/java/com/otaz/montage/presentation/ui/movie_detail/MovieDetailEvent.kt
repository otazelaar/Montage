package com.otaz.montage.presentation.ui.movie_detail

/**
 * This class is used to trigger events related to the MovieDetails
 */

sealed class MovieDetailEvent {

    data class GetTmdbMovieDetailEvent(
        val tmdb_id: Int,
    ): MovieDetailEvent()

    object NextPageDetailEvent: MovieDetailEvent()

}