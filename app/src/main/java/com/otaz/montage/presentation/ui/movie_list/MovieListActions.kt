package com.otaz.montage.presentation.ui.movie_list

import com.otaz.montage.domain.model.Movie

sealed class MovieListActions {
    object NewSearch: MovieListActions()
    object NextPage: MovieListActions()
    data class SaveMovie(val movie: Movie): MovieListActions()
    object ResetForNewSearch: MovieListActions()
    data class CategoryChanged(val category: String): MovieListActions()
    data class QueryChanged(val query: String): MovieListActions()
    data class MovieScrollPositionChanged(val position: Int): MovieListActions()
}