package com.otaz.montage.presentation.ui.saved_movie_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaz.montage.domain.model.Movie
import com.otaz.montage.interactors.app.DeleteMovie
import com.otaz.montage.interactors.app.GetSavedMovies
import com.otaz.montage.presentation.ui.saved_movie_list.SavedMovieListEvent.*
import com.otaz.montage.presentation.ui.util.DialogQueue
import com.otaz.montage.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedMovieListViewModel @Inject constructor(
    private val getSavedMovies: GetSavedMovies,
    private val deleteMovie: DeleteMovie,
): ViewModel() {
    val savedMovies: MutableState<List<Movie>> = mutableStateOf(ArrayList())

    val loading = mutableStateOf(false)

    private val dialogQueue = DialogQueue()

    init {
        getSavedMovies()
//        the list of save movies only updates when the app is closed and reopened
//        placing the getSaveMovies() below as a MovieListEvent results in the list not being updated until
//        clicking on a new movie to save
    }

    fun onTriggerEvent(event: SavedMovieListEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is UpdateSavedMoviesList -> {
                        getSavedMovies()
                    }
                    is DeleteSavedMovie -> {
                        deleteSavedMovie(event.id)
                    }
                }
            }catch (e: Exception){
                Log.e(TAG, "SavedMovieListViewModel: onTriggerEvent: Exception ${e}, ${e.cause}")
            }
        }
    }

    private fun getSavedMovies(){
        Log.d(TAG, "SavedMoviesListViewModel: getSavedMovies: running")

        getSavedMovies.execute().onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let { list -> savedMovies.value = list }
            dataState.error?.let { error -> dialogQueue.appendErrorMessage("SavedMoviesListViewModel: getSavedMovies: Error:", error)}
        }.launchIn(viewModelScope)
    }

    private suspend fun deleteSavedMovie(id: String){
        Log.d(TAG, "SavedMoviesListViewModel: deleteSavedMovie:$id running")

        deleteMovie.execute(
            id = id
        )

        // Update saved movies list - I think this should do that. Otherwise might need to
        // update list from UI when movie is deleted clicked. this might not be the spot to do that
        getSavedMovies()
    }
}