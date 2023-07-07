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

    init { getSavedMovies() }

    fun onTriggerEvent(event: SavedMovieListEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is UpdateSavedMoviesList -> getSavedMovies()
                    is DeleteSavedMovie -> deleteSavedMovie(event.id)
                }
            }catch (e: Exception){
                Log.e(TAG, "SavedMovieListViewModel: onTriggerEvent: Exception ${e}, ${e.cause}")
            }
        }
    }

    private fun getSavedMovies(){
        getSavedMovies.execute().onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let { list -> savedMovies.value = list }
            dataState.error?.let { error -> Log.e(TAG, error)}
        }.launchIn(viewModelScope)
    }

    private suspend fun deleteSavedMovie(id: String){
        deleteMovie.execute(id = id)
        getSavedMovies()
    }
}