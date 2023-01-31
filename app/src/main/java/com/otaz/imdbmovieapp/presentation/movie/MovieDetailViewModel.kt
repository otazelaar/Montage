package com.otaz.imdbmovieapp.presentation.movie

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaz.imdbmovieapp.domain.model.Movie
import com.otaz.imdbmovieapp.presentation.movie.MovieEvent.*
import com.otaz.imdbmovieapp.repository.MovieRepository
import com.otaz.imdbmovieapp.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val STATE_KEY_MOVIE = "movie.state.movie.key"

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository,
    @Named("apiKey") private val apiKey: String,
    private val state: SavedStateHandle,
): ViewModel(){

    val onLoad: MutableState<Boolean> = mutableStateOf(false)

    val movie: MutableState<Movie?> = mutableStateOf(null)

    val loading = mutableStateOf(false)

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

    private suspend fun getMovie(id: String){
        loading.value = true

        // simulate a delay to show loading
        delay(1000)

        val movie = repository.get(
            apikey = apiKey,
            id = id,
        )
        this.movie.value = movie

        state.set(STATE_KEY_MOVIE, movie.id)

        loading.value = false
    }
}