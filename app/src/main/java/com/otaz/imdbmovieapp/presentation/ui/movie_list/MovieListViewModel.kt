package com.otaz.imdbmovieapp.presentation.ui.movie_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaz.imdbmovieapp.domain.model.Movie
import com.otaz.imdbmovieapp.interactors.movie_list.RestoreMovies
import com.otaz.imdbmovieapp.interactors.movie_list.SearchMovies
import com.otaz.imdbmovieapp.presentation.ui.movie_list.MovieListEvent.*
import com.otaz.imdbmovieapp.presentation.ui.util.DialogQueue
import com.otaz.imdbmovieapp.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val PAGE_SIZE = 10

const val STATE_KEY_PAGE = "recipe.state.page.key"
const val STATE_KEY_QUERY = "recipe.state.query.key"
const val STATE_KEY_LIST_POSITION = "recipe.state.query.list_position"
const val STATE_KEY_SELECTED_CATEGORY = "recipe.state.query.selected_category"

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val searchMovies: SearchMovies,
    private val restoreMovies: RestoreMovies,
    @Named("apikey") private val apiKey: String,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {

    val movies: MutableState<List<Movie>> = mutableStateOf(ArrayList())

    val query = mutableStateOf("")

    val selectedCategory: MutableState<MovieCategory?> = mutableStateOf(null)

    var categoryScrollPosition: Int = 0

    val loading = mutableStateOf(false)

    // Pagination
    val page = mutableStateOf(1)
    // due to MOVIE_PAGINATION_PAGE_SIZE = 10 at this time. Not sure if I can set a mutableStateOf to a const val
    var movieListScrollPosition = 0

    val dialogQueue = DialogQueue()

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

//      Were they doing something before the process died?
        if(movieListScrollPosition != 0){
            onTriggerEvent(RestoreStateEvent)
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

    private fun restoreState(){
        restoreMovies.execute(
            query = query.value,
            page = page.value,
        ).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let { list -> movies.value = list }
            dataState.error?.let { error -> dialogQueue.appendErrorMessage("Error", error) }
        }.launchIn(viewModelScope)
    }

    private fun newSearch(){
        Log.d(TAG, "newSearch: query: ${query.value}, page: ${page.value}")

        resetSearchState()
        searchMovies.execute(
            apikey = apiKey,
            query = query.value,
            page = page.value,
        ).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let { list -> movies.value = list }
            dataState.error?.let { error -> dialogQueue.appendErrorMessage("Error1", error)
                dialogQueue.appendErrorMessage("Error2", error)
                dialogQueue.appendErrorMessage("Error3", error)}
        }.launchIn(viewModelScope)
    }

    private fun nextPage(){
        if((movieListScrollPosition + 1) >= (page.value * PAGE_SIZE)){
            incrementPage()
            Log.d(TAG, "nextPage: triggered: ${page.value}")

            if(page.value > 1){
                searchMovies.execute(
                    apikey = apiKey,
                    query = query.value,
                    page = page.value,
                ).onEach { dataState ->
                    loading.value = dataState.loading
                    dataState.data?.let { list -> appendMovies(list) }
                    dataState.error?.let { error -> dialogQueue.appendErrorMessage("Error", error) }
                }.launchIn(viewModelScope)
            }
        }
    }

    /**
     * Append new movies to the current list of movies. This may be inefficiently calling
     * extra data and appending the desired data to the list. For Ex: if my page size = 10
     * and i go to the second page, I may be calling the first 10 movies as well as the second 10
     * and then appending only the second 10. I am not clear on if this is the case yet.
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

    /**
     * Called when a new search is executed
     */
    private fun resetSearchState(){
        movies.value = listOf()
        page.value = 1
        onChangeMovieScrollPosition(0)
        // might need to reset index here as well
        if(selectedCategory.value?.value != query.value) clearSelectedCategory()
    }

    private fun clearSelectedCategory(){
        setSelectedCategory(null)
        selectedCategory.value = null
    }

    fun onQueryChanged(query: String){
        setQuery(query)
    }

    fun onSelectedCategoryChanged(category: String){
        val newCategory = getMovieCategory(category)
        setSelectedCategory(newCategory)
        onQueryChanged(category)
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
        this.query.value = query
        savedStateHandle.set(STATE_KEY_QUERY, query)
    }
}