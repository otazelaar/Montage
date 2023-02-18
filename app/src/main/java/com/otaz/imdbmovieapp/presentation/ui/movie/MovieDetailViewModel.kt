package com.otaz.imdbmovieapp.presentation.ui.movie

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaz.imdbmovieapp.domain.model.ImageConfigs
import com.otaz.imdbmovieapp.domain.model.MovieSpecs
import com.otaz.imdbmovieapp.interactors.app.GetConfigurations
import com.otaz.imdbmovieapp.interactors.movie.GetMovie
import com.otaz.imdbmovieapp.presentation.ui.movie.MovieEvent.*
import com.otaz.imdbmovieapp.presentation.ui.util.DialogQueue
import com.otaz.imdbmovieapp.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovie: GetMovie,
    private val getConfigurations: GetConfigurations,
    @Named("api_key") private val apiKey: String,
): ViewModel(){

    val onLoad: MutableState<Boolean> = mutableStateOf(false)

    val movie: MutableState<MovieSpecs?> = mutableStateOf(null)
    val configurations: MutableState<ImageConfigs> = mutableStateOf(GetConfigurations.EMPTY_CONFIGURATIONS)

    val loading = mutableStateOf(false)

    val dialogQueue = DialogQueue()

    init {
        getConfigurations()
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

    private fun getMovie(id: Int){
        getMovie.execute(
            apikey = apiKey,
            id = id,
        ).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let {
                data -> movie.value = data
                Log.i(TAG, "getMovie: Success: ${data.id}")
            }
            dataState.error?.let { error -> dialogQueue.appendErrorMessage("Error", error) }
        }.launchIn(viewModelScope)
    }

    private fun getConfigurations(){
        Log.d(TAG, "getConfigurations running")

        getConfigurations.execute(
            apikey = apiKey,
        ).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let { value -> configurations.value = value }
            dataState.error?.let { error -> dialogQueue.appendErrorMessage("GetConfigurations Error", error)}
        }.launchIn(viewModelScope)
    }
}