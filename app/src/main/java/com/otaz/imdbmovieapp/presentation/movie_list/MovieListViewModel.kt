package com.otaz.imdbmovieapp.presentation.movie_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaz.imdbmovieapp.domain.model.Movie
import com.otaz.imdbmovieapp.network.model.MovieDtoMapper
import com.otaz.imdbmovieapp.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieRepository,
    @Named("api_key") private val apiKey: String
): ViewModel() {

    val movies: MutableState<List<Movie>> = mutableStateOf(listOf())

    init {
        viewModelScope.launch {
            val result = repository.search(
                apikey = apiKey,
                query = "iron",
            )
            movies.value = result
        }
    }

}