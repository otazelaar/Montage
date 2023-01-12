package com.otaz.imdbmovieapp.presentation.movie_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaz.imdbmovieapp.domain.model.Movie
import com.otaz.imdbmovieapp.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieRepository,
    @Named("apiKey") private val apiKey: String
): ViewModel() {

    val movies: MutableState<List<Movie>> = mutableStateOf(ArrayList())

    val expression = mutableStateOf("Godfather")

    init {
        newSearch("Interstellar")
    }

    fun newSearch(expression: String){
        viewModelScope.launch {
            val result = repository.search(
                apikey = apiKey,
                expression = expression,
            )
            movies.value = result
        }
    }

    fun onExpressionChanged(expression: String){
        this.expression.value = expression
    }
}