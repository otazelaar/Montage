package com.otaz.imdbmovieapp.presentation.ui.movie

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaz.imdbmovieapp.domain.model.MovieSpecs
import com.otaz.imdbmovieapp.interactors.movie.GetMovie
import com.otaz.imdbmovieapp.presentation.ui.movie.MovieEvent.*
import com.otaz.imdbmovieapp.presentation.ui.util.DialogQueue
import com.otaz.imdbmovieapp.presentation.util.ConnectivityManager
import com.otaz.imdbmovieapp.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val STATE_KEY_MOVIE = "movie.state.movie.key"
const val STATE_KEY_MOVIE_SPECS = "movie.state.movie.specs.key"

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovie: GetMovie,
    private val connectivityManager: ConnectivityManager,
    @Named("apikey") private val apiKey: String,
    private val state: SavedStateHandle,
): ViewModel(){

    val onLoad: MutableState<Boolean> = mutableStateOf(false)

    val movie: MutableState<MovieSpecs?> = mutableStateOf(null)

    val loading = mutableStateOf(false)

    val dialogQueue = DialogQueue()

    init {
        // restore if process dies
        state.get<String>(STATE_KEY_MOVIE)?.let{ movieTitle ->
            onTriggerEvent(GetMovieEvent(movieTitle))
        }
    }

    fun onTriggerEvent(event: MovieEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is GetMovieEvent -> {
                        if(movie.value == null){
                            getMovie(event.id)
                        }
                    }
                }
            }catch (e: Exception){
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
        }
    }

    private fun getMovie(id: String){
        getMovie.execute(
            apikey = apiKey,
            id = id,
            isNetworkAvailable = connectivityManager.isNetworkAvailable.value,
        ).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let {
                data -> movie.value = data
                state.set(STATE_KEY_MOVIE_SPECS, data.id)
                Log.i(TAG, "getMovie: Success: ${data.id}")
            }
            dataState.error?.let { error -> dialogQueue.appendErrorMessage("Error", error) }
        }.launchIn(viewModelScope)
    }
}