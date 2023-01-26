package com.otaz.imdbmovieapp.presentation.movie_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaz.imdbmovieapp.domain.model.Movie
import com.otaz.imdbmovieapp.domain.model.Poster
import com.otaz.imdbmovieapp.presentation.movie_list.MovieListEvent.*
import com.otaz.imdbmovieapp.repository.MovieRepository
import com.otaz.imdbmovieapp.repository.PosterRepository
import com.otaz.imdbmovieapp.util.TAG
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val PAGE_SIZE = 30

const val STATE_KEY_PAGE = "recipe.state.page.key"
const val STATE_KEY_QUERY = "recipe.state.query.key"
const val STATE_KEY_LIST_POSITION = "recipe.state.query.list_position"
const val STATE_KEY_SELECTED_CATEGORY = "recipe.state.query.selected_category"

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val repositoryPoster: PosterRepository,
    @Named("apiKey") private val apiKey: String,
    private val savedStateHandle: SavedStateHandle,
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
    var movieListScrollPosition = 0

    init {
        savedStateHandle.get<Int>(STATE_KEY_PAGE)?.let { p ->
            Log.d(TAG, "restoring page: ${p}")
            setPage(p)
        }
        savedStateHandle.get<String>(STATE_KEY_QUERY)?.let { q ->
            setQuery(q)
        }
        savedStateHandle.get<Int>(STATE_KEY_LIST_POSITION)?.let { p ->
            Log.d(TAG, "restoring scroll position: ${p}")
            setListScrollPosition(p)
        }
        savedStateHandle.get<MovieCategory>(STATE_KEY_SELECTED_CATEGORY)?.let { c ->
            setSelectedCategory(c)
        }

//         Were they doing something before the process died?
        if(movieListScrollPosition != 0){
            onTriggerEvent(RestoreStateEvent)
        }
        else{
            onTriggerEvent(NewSearchEvent)
        }
    }

    fun onTriggerEvent(event: MovieListEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is NewSearchEvent -> {
                        newSearch()
                    }
                    is NextPageEvent -> {
                        nextPage()
                    }
                    is RestoreStateEvent -> {
                        restoreState()
                    }
                }
            }catch (e: Exception){
                Log.e(TAG, "onTriggerEvent: Exception ${e}, ${e.cause}")
            }
        }
    }

    private suspend fun restoreState(){
        loading.value = true
        // Must retrieve each page of results.
        val results: MutableList<Movie> = mutableListOf()
        for(p in 1..page.value){
            Log.d(TAG, "restoreState: page: ${p}, query: ${expression.value}")
            val result = repository.search(apikey = apiKey, expression = expression.value, count = expression.value )
            results.addAll(result)
            if(p == page.value){ // done
                movies.value = results
                loading.value = false
            }
        }
    }

    private suspend fun newSearch(){
        loading.value = true
        resetSearchState()

        val result = repository.search(
            apikey = apiKey,
            expression = expression.value,
            count = (page.value * PAGE_SIZE).toString(),
        )
        movies.value = result
        loading.value = false
    }

    private suspend fun nextPage(){
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
                    // api is NOT calling the next set of 30 movies. it is calling the original 30 plus 30 more.
                    // the paging feature on Mitch's api is different from mine. Each page number is a new set of Recipes in his API.
                )
                Log.d(TAG, "nextPage: ${result}")
                appendMovies(result)
            }
            loading.value = false
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
        setPage(page.value + 1)
    }

    fun onChangeMovieScrollPosition(position: Int){
        setListScrollPosition(position = position)
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
        if(selectedCategory.value?.value != expression.value) clearSelectedCategory()
    }

    private fun clearSelectedCategory(){
        setSelectedCategory(null)
        selectedCategory.value = null
    }

    fun onExpressionChanged(expression: String){
        setQuery(expression)
    }

    fun onSelectedCategoryChanged(category: String){
        val newCategory = getMovieCategory(category)
        setSelectedCategory(newCategory)
        onExpressionChanged(category)
    }

    fun onChangedCategoryScrollPosition(position: Int){
        categoryScrollPosition = position
    }

    /**
     * The following functions are for reading and writing to the savedStateHandle in the case of process death.
     * This allows us to restore the state of the the user's experience in the app. For example, it saves the of the scroll
     * position on the list, the page of the list for pagination, the selected category chip, and the expression entered into
     * the search input.
     */

    private fun setListScrollPosition(position: Int){
        movieListScrollPosition = position
        savedStateHandle.set(STATE_KEY_LIST_POSITION, position)
    }

    private fun setPage(page: Int){
        this.page.value = page
        savedStateHandle.set(STATE_KEY_PAGE, page)
    }

    private fun setSelectedCategory(category: MovieCategory?){
        selectedCategory.value = category
        savedStateHandle.set(STATE_KEY_SELECTED_CATEGORY, category)
    }

    private fun setQuery(query: String){
        this.expression.value = query
        savedStateHandle.set(STATE_KEY_QUERY, query)
    }
}