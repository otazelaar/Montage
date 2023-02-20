package com.otaz.imdbmovieapp.presentation.ui.movie_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaz.imdbmovieapp.domain.model.ImageConfigs
import com.otaz.imdbmovieapp.domain.model.Movie
import com.otaz.imdbmovieapp.interactors.app.GetConfigurations
import com.otaz.imdbmovieapp.interactors.movie_list.GetMostPopularMovies
import com.otaz.imdbmovieapp.interactors.movie_list.GetUpcomingMovies
import com.otaz.imdbmovieapp.interactors.movie_list.SearchMovies
import com.otaz.imdbmovieapp.presentation.ui.movie_list.MovieListEvent.*
import com.otaz.imdbmovieapp.presentation.ui.util.DialogQueue
import com.otaz.imdbmovieapp.util.MOVIE_PAGINATION_PAGE_SIZE
import com.otaz.imdbmovieapp.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val searchMovies: SearchMovies,
    private val getConfigurations: GetConfigurations,
    private val getMostPopularMovies: GetMostPopularMovies,
    private val getUpcomingMovies: GetUpcomingMovies,
    @Named("api_key") private val apiKey: String,
): ViewModel() {

    val movies: MutableState<List<Movie>> = mutableStateOf(ArrayList())
    val configurations: MutableState<ImageConfigs> = mutableStateOf(GetConfigurations.EMPTY_CONFIGURATIONS)

    val query = mutableStateOf("")
    private val sortingParameterPopularityDescending = "popularity.desc"

    val selectedCategory: MutableState<MovieCategory?> = mutableStateOf(null)
    var categoryScrollPosition: Int = 0

    val loading = mutableStateOf(false)

    // Pagination
    val page = mutableStateOf(1)
    var movieListScrollPosition = 0

    // Error Handling
    val dialogQueue = DialogQueue()

    init {
        getConfigurations()
    }

    fun onTriggerEvent(event: MovieListEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is NewSearchEvent -> {
                        // The function below abstracts out the if statements for deciding which use case/api call to use for the search
                        // for example if a category is picked that requires a different use case than the traditional search bar is using,
                        // then it uses the selected category to tell the onTriggerEvent which UseCase to use.
                        newSearchUseCasePicker()
                    }
                    is NextPageEvent -> {
                        nextPage()
                    }
                }
            }catch (e: Exception){
                Log.e(TAG, "onTriggerEvent: Exception ${e}, ${e.cause}")
            }
        }
    }

    private fun newSearch(){
        Log.d(TAG, "newSearch: query: ${query.value}, page: ${page.value}")

        resetSearchState()
        searchMovies.execute(
            apikey = apiKey,
            query = query.value,
            page = page.value
        ).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let { list -> movies.value = list }
            dataState.error?.let { error -> dialogQueue.appendErrorMessage("newSearch: Error:", error)}
        }.launchIn(viewModelScope)
    }

    private fun getMostPopularMovies(){
        Log.d(TAG, "getMostPopularMovies: query: ${query.value}, page: ${page.value}")

        resetSearchState()
        getMostPopularMovies.execute(
            apikey = apiKey,
            sortBy = sortingParameterPopularityDescending,
            page = page.value
        ).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let { list -> movies.value = list }
            dataState.error?.let { error -> dialogQueue.appendErrorMessage("getMostPopularMovies: Error:", error)}
        }.launchIn(viewModelScope)
    }

    private fun getUpcomingMovies(){
        Log.d(TAG, "getUpcomingMovies: query: ${query.value}, page: ${page.value}")

        resetSearchState()
        getUpcomingMovies.execute(
            apikey = apiKey,
            page = page.value
        ).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let { list -> movies.value = list }
            dataState.error?.let { error -> dialogQueue.appendErrorMessage("getUpcomingMovies: Error:", error)}
        }.launchIn(viewModelScope)
    }

    private fun nextPage(){
        if((movieListScrollPosition + 1) >= (page.value * MOVIE_PAGINATION_PAGE_SIZE)){
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

    private fun newSearchUseCasePicker(){
        val movieCategorySelected = selectedCategory.value
        val getMostPopularMovies = getMovieCategory(MovieCategory.GET_MOST_POPULAR_MOVIES.value)
        val getUpcomingMovies = getMovieCategory(MovieCategory.GET_UPCOMING_MOVIES.value)

        when (movieCategorySelected) {
            getMostPopularMovies -> {
                getMostPopularMovies()
            }
            getUpcomingMovies -> {
                getUpcomingMovies()
            }
            else -> {
                newSearch()
            }
        }
    }

    private fun setListScrollPosition(position: Int){
        movieListScrollPosition = position
    }

    private fun setPage(page: Int){
        this.page.value = page
    }

    private fun setSelectedCategory(category: MovieCategory?){
        selectedCategory.value = category
    }

    private fun setQuery(query: String){
        this.query.value = query
    }
}