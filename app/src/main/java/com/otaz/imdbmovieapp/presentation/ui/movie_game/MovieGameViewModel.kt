package com.otaz.imdbmovieapp.presentation.ui.movie_game

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaz.imdbmovieapp.domain.model.ImageConfigs
import com.otaz.imdbmovieapp.domain.model.Movie
import com.otaz.imdbmovieapp.domain.model.TmdbMovieSpecs
import com.otaz.imdbmovieapp.interactors.app.GetConfigurations
import com.otaz.imdbmovieapp.interactors.movie_game.GetRandomTopRatedMovie
import com.otaz.imdbmovieapp.presentation.ui.movie_game.MovieGameEvent.NewRandomMovieEvent
import com.otaz.imdbmovieapp.presentation.ui.util.DialogQueue
import com.otaz.imdbmovieapp.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

// create a use case for randomly calling a movie from the top rated movie search presets
// model UI to be a game for guessing the date of the movie

// For now just make a game that accesses saved movies from the room database from your watch list.
// We can add popular movies later.

@HiltViewModel
class MovieGameViewModel @Inject constructor(
    private val getConfigurations: GetConfigurations,
    private val getRandomTopRatedMovie: GetRandomTopRatedMovie,
    @Named("tmdb_apikey") private val tmdb_apiKey: String,
): ViewModel() {
    // iterated through the index of this list of movies randomly to pick a movie. Create a function
    // named random movie picker to do this at the bottom of this viewmodel

    val movie: MutableState<Movie?> = mutableStateOf(null)
    val configurations: MutableState<ImageConfigs> = mutableStateOf(GetConfigurations.EMPTY_CONFIGURATIONS)

    val onLoad: MutableState<Boolean> = mutableStateOf(false)
    val loading = mutableStateOf(false)
    val dialogQueue = DialogQueue()

    init {
        getConfigurations()
        getRandomTopRatedMovie()
    }

    fun onTriggerEvent(event: MovieGameEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is NewRandomMovieEvent -> {
                        getRandomTopRatedMovie()
                    }
                }
            }catch (e: Exception){
                Log.e(TAG, "MovieGameViewModel: onTriggerEvent: Exception ${e}, ${e.cause}")
            }
        }
    }

    /**
     * Retrieves a random movie from the Room database through the "getRandomTopRatedMovie" use case
     */
    private fun getRandomTopRatedMovie(){
        Log.d(TAG, "MovieGameViewModel: getRandomTopRatedMovies: Running")

        getRandomTopRatedMovie.execute(
        ).onEach { data ->
            loading.value = data.loading
            data.data?.let { it -> movie.value = it }
            data.error?.let { error -> dialogQueue.appendErrorMessage("MovieGameViewModel: getRandomTopRatedMovies: Error:", error)}
        }.launchIn(viewModelScope)
    }

    private fun getConfigurations(){
        Log.d(TAG, "MovieGameViewModel: getConfigurations: running")

        getConfigurations.execute(
            apikey = tmdb_apiKey,
        ).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let { value -> configurations.value = value }
            dataState.error?.let { error -> dialogQueue.appendErrorMessage("MovieGameViewModel: GetConfigurations: Error", error)}
        }.launchIn(viewModelScope)
    }
}