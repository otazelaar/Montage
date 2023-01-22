package com.otaz.imdbmovieapp.presentation.movie_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaz.imdbmovieapp.domain.model.Movie
import com.otaz.imdbmovieapp.domain.model.Poster
import com.otaz.imdbmovieapp.repository.MovieRepository
import com.otaz.imdbmovieapp.repository.PosterRepository
import com.otaz.imdbmovieapp.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val PAGE_SIZE = 30

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

    // Pagination
    val page = mutableStateOf(1)
    private var movieListScrollPosition = 0

    init {
        newSearch()
//        getPosterByID()
    }

    fun newSearch(){
        viewModelScope.launch {

            loading.value = true
            resetSearchState()
            delay(2000)

            val result = repository.search(
                apikey = apiKey,
                expression = expression.value,
                count = (page.value * PAGE_SIZE).toString(),
            )
            movies.value = result
            loading.value = false
        }
    }

    fun nextPage(){
        viewModelScope.launch {
            // Prevent duplicate events due to recompose happening too quickly
            if((movieListScrollPosition + 1) >= (page.value * PAGE_SIZE)){
                loading.value = true
                incrementPage()
                Log.d(TAG, "nextPage: triggered: ${page.value}")

                if (page.value > 1){
                    val result = repository.search(
                        apikey = apiKey,
                        expression = expression.value,
                        count = (page.value * PAGE_SIZE).toString(),
                    )
                    Log.d(TAG, "nextPage: ${result}")
                    appendMovies(result)
                }
                loading.value = false
            }
        }
    }

    /**
     * Append new movies to the current list of movies
     */
    private fun appendMovies(movies: List<Movie>){
        val currentList = ArrayList(this.movies.value)
        currentList.addAll(movies)
        this.movies.value = currentList
    }

    private fun incrementPage(){
        page.value = page.value + 1
    }

    fun onChangeMovieScrollPosition(position: Int){
        movieListScrollPosition = position
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

    /**
     * Called when a new search is executed
     */
    private fun resetSearchState(){
        movies.value = listOf()
        page.value = 1
        onChangeMovieScrollPosition(0)
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