package com.otaz.imdbmovieapp.presentation.ui.saved_movie_list

/**
 * This class is used to trigger events related to the SavedMovieList
 */

sealed class SavedMovieListEvent {
    object UpdateSavedMoviesList: SavedMovieListEvent()
    data class DeleteSavedMovie(val id: String): SavedMovieListEvent()
}