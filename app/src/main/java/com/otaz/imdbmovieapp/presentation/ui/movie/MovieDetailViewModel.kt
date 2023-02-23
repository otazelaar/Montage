package com.otaz.imdbmovieapp.presentation.ui.movie

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaz.imdbmovieapp.domain.model.ImageConfigs
import com.otaz.imdbmovieapp.domain.model.OmdbMovieSpecs
import com.otaz.imdbmovieapp.domain.model.TmdbMovieSpecs
import com.otaz.imdbmovieapp.interactors.app.GetConfigurations
import com.otaz.imdbmovieapp.interactors.movie.GetTmdbMovie
import com.otaz.imdbmovieapp.interactors.movie.GetOmdbMovie
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
    private val getTmdbMovie: GetTmdbMovie,
    private val getOmdbMovie: GetOmdbMovie,
    private val getConfigurations: GetConfigurations,
    @Named("tmdb_apikey") private val tmdb_apiKey: String,
    @Named("omdb_apikey") private val omdb_apiKey: String,
    ): ViewModel(){

    val movieTmdb: MutableState<TmdbMovieSpecs?> = mutableStateOf(null)
    val movieOmdb: MutableState<OmdbMovieSpecs?> = mutableStateOf(null)
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
                    is GetTmdbMovieEvent -> {
                        if(movieTmdb.value == null){
                            this@MovieDetailViewModel.getTmdbMovie(event.tmdb_id)
                        }
                    }
                    is GetOmdbMovieEvent -> {
                        this@MovieDetailViewModel.getOmdbMovie(event.omdb_id)
                        Log.i(TAG, "GetOmdbMovieEvent Triggered")
                    }
                }
            }catch (e: Exception){
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
        }
    }

    private fun getTmdbMovie(id: Int){
        getTmdbMovie.execute(
            tmdb_apikey = tmdb_apiKey,
            id = id,
        ).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let {
                data -> movieTmdb.value = data
                Log.i(TAG, "getTmdbMovie: Success: ${data.id}")
            }
            dataState.error?.let { error -> dialogQueue.appendErrorMessage("getTmdbMovie: Error", error) }
        }.launchIn(viewModelScope)
    }

    private fun getOmdbMovie(omdb_id: String){
        getOmdbMovie.execute(
            omdb_apikey = omdb_apiKey,
            omdb_id = omdb_id,
        ).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let {
                data -> movieOmdb.value = data
                Log.i(TAG, "getOmdbMovie: Success: ${data.id}")
            }
            dataState.error?.let { error -> dialogQueue.appendErrorMessage("getOmdbMovie: Error", error) }
        }.launchIn(viewModelScope)
    }

    private fun getConfigurations(){
        Log.d(TAG, "getConfigurations running")

        getConfigurations.execute(
            apikey = tmdb_apiKey,
        ).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let { value -> configurations.value = value }
            dataState.error?.let { error -> dialogQueue.appendErrorMessage("GetConfigurations Error", error)}
        }.launchIn(viewModelScope)
    }
}