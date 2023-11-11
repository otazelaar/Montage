package com.otaz.montage.presentation.ui.movie_list

import com.otaz.montage.domain.model.Movie

sealed class MovieListActions {
    object NewSearch: MovieListActions()
    object ResetForNewSearch: MovieListActions()
    data class CategoryChanged(val category: String): MovieListActions()
    data class QueryChanged(val query: String): MovieListActions()
    object NextPage: MovieListActions()
    data class MovieScrollPositionChanged(val position: Int): MovieListActions()
    data class CacheMovieAction(val movie: Movie): MovieListActions()
    data class DeleteSavedMovie(val id: String): MovieListActions()
    object GetAllSavedMovies: MovieListActions()
    data class SaveMovie(val movie: Movie): MovieListActions()
}