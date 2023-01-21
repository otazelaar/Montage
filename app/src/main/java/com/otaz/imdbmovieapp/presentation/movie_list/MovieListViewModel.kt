package com.otaz.imdbmovieapp.presentation.movie_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaz.imdbmovieapp.domain.model.Movie
import com.otaz.imdbmovieapp.domain.model.Poster
import com.otaz.imdbmovieapp.repository.MovieRepository
import com.otaz.imdbmovieapp.repository.PosterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val repositoryPoster: PosterRepository,
    @Named("apiKey") private val apiKey: String
): ViewModel() {

    val expression = mutableStateOf("")
    val movies: MutableState<List<Movie>> = mutableStateOf(ArrayList())

    val id = mutableStateOf("")
    val poster: MutableState<List<Poster>> = mutableStateOf(ArrayList())

    val selectedCategory: MutableState<MovieCategory?> = mutableStateOf(null)

    var categoryScrollPosition: Int = 0

    val loading = mutableStateOf(false)

    init {
        newSearch()
        getPosterByID()
    }

    fun newSearch(){
        viewModelScope.launch {

            loading.value = true
            resetSearchState()
            delay(2000)

            val result = repository.search(
                apikey = apiKey,
                expression = expression.value,
            )
            movies.value = result
            loading.value = false
        }
    }

    fun getPosterByID(){
        viewModelScope.launch {

            val result = repositoryPoster.poster(
                apikey = apiKey,
                id = id.value,
            )
            poster.value = result

        }
    }

    private fun resetSearchState(){
        movies.value = listOf()
        if(selectedCategory.value?.value != expression.value)
            clearSelectedCategory()
    }

    private fun clearSelectedCategory(){
        selectedCategory.value = null
    }

    fun onExpressionChanged(expression: String){
        this.expression.value = expression
    }

    fun onSelectedCategoryChanged(category: String){
        val newCategory = getMovieCategory(category)
        selectedCategory.value = newCategory
        onExpressionChanged(category)
    }

    fun onChangedCategoryScrollPosition(position: Int){
        categoryScrollPosition = position
    }
}