package com.otaz.montage.presentation.ui.movie_list

import com.otaz.montage.domain.model.Movie

/**
 * This class is used to trigger events related to the MovieList
 */

sealed class MovieListEvents {
    object NewSearch: MovieListEvents()
    object NextPage: MovieListEvents()
    data class SaveMovie(val movie: Movie): MovieListEvents()
}