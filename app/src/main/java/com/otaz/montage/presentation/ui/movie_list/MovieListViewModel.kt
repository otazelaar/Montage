package com.otaz.montage.presentation.ui.movie_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaz.montage.domain.model.ImageConfigs
import com.otaz.montage.domain.model.Movie
import com.otaz.montage.interactors.app.GetConfigurations
import com.otaz.montage.interactors.app.SaveMovie
import com.otaz.montage.interactors.movie_list.GetMostPopularMovies
import com.otaz.montage.interactors.movie_list.GetTopRatedMovies
import com.otaz.montage.interactors.movie_list.GetUpcomingMovies
import com.otaz.montage.interactors.movie_list.SearchMovies
import com.otaz.montage.presentation.ui.movie_list.MovieListEvent.*
import com.otaz.montage.util.MOVIE_PAGINATION_PAGE_SIZE
import com.otaz.montage.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val saveMovie: SaveMovie,
    private val searchMovies: SearchMovies,
    private val getConfigurations: GetConfigurations,
    private val getMostPopularMovies: GetMostPopularMovies,
    private val getUpcomingMovies: GetUpcomingMovies,
    private val getTopRatedMovies: GetTopRatedMovies,
    @Named("tmdb_apikey") private val apiKey: String,
): ViewModel() {
    val movies: MutableState<List<Movie>> = mutableStateOf(ArrayList())
    val configurations: MutableState<ImageConfigs> = mutableStateOf(GetConfigurations.EMPTY_CONFIGURATIONS)
    val selectedCategory: MutableState<MovieCategory?> = mutableStateOf(null)

    val query = mutableStateOf("")
    private val sortingParameterPopularityDescending = "popularity.desc"

    var categoryScrollPosition: Int = 0

    val loading = mutableStateOf(false)

    val page = mutableStateOf(1)
    var movieListScrollPosition = 0

    init {
        getConfigurations()
        getMostPopularMovies()
    }

    fun onTriggerEvent(event: MovieListEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is NewSearchEvent -> newSearchUseCasePicker()
                    is NextPageEvent -> nextPage()
                    is SaveMovieEvent -> saveMovie(movie = event.movie)
                }
            }catch (e: Exception){
                Log.e(TAG, "MovieListViewModel: onTriggerEvent: Exception ${e}, ${e.cause}")
            }
        }
    }

    private fun newSearch(){
        resetSearchState()
        searchMovies.execute(
            apikey = apiKey,
            query = query.value,
            page = page.value
        ).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let { list -> movies.value = list }
            dataState.error?.let { error -> Log.e("newSearch: Error:", error)}
        }.launchIn(viewModelScope)
    }

    private fun getMostPopularMovies(){
        resetSearchState()
        getMostPopularMovies.execute(
            apikey = apiKey,
            sortBy = sortingParameterPopularityDescending,
            page = page.value
        ).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let { list -> movies.value = list }
            dataState.error?.let { error -> Log.e(TAG, error)}
        }.launchIn(viewModelScope)
    }

    private fun getUpcomingMovies(){
        resetSearchState()
        getUpcomingMovies.execute(
            apikey = apiKey,
            page = page.value
        ).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let { list -> movies.value = list }
            dataState.error?.let { error -> Log.e(TAG, error)}
        }.launchIn(viewModelScope)
    }

    private fun getTopRatedMovies(){
        resetSearchState()
        getTopRatedMovies.execute(
            apikey = apiKey,
            page = page.value
        ).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let { list -> movies.value = list }
            dataState.error?.let { error -> Log.e(TAG, error)}
        }.launchIn(viewModelScope)
    }

    private fun nextPage(){
        if((movieListScrollPosition + 1) >= (page.value * MOVIE_PAGINATION_PAGE_SIZE)){
            incrementPage()
            Log.d(TAG, "MovieListViewModel: nextPage: triggered: ${page.value}")

            if(page.value > 1){
                searchMovies.execute(
                    apikey = apiKey,
                    query = query.value,
                    page = page.value,
                ).onEach { dataState ->
                    loading.value = dataState.loading
                    dataState.data?.let { list -> appendMovies(list) }
                    dataState.error?.let { error -> Log.e(TAG, error) }
                }.launchIn(viewModelScope)
            }
        }
    }

    private fun getConfigurations(){
        getConfigurations.execute(
            apikey = apiKey,
        ).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let { value -> configurations.value = value }
            dataState.error?.let { error -> Log.e(TAG, error)}
        }.launchIn(viewModelScope)
    }

    private suspend fun saveMovie(movie: Movie){
        saveMovie.execute(movie = movie)
    }

    // Append new movies to the current list of movies.
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
     * Called when a new search is executed and when Icon in SearchAppBar is clicked
     * This will reset search state. if this doesn't work then i need to make a separate function that
     * at least will clear the search query
     */
    private fun resetSearchState(){
        movies.value = listOf()
        page.value = 1
        onChangeMovieScrollPosition(0)
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
     * The function below abstracts out the if statements for deciding which use case/api call to
     * use for the search for example if a category is picked that requires a different use case
     * than the traditional search bar is using, then it uses the selected category to tell the
     * onTriggerEvent which UseCase to use.
     *
     * textViewQuery is needed to determine if user is attempting to search for a movie after having
     * searched using a MovieCategoryChip.
     */
    private fun newSearchUseCasePicker(){
        val textViewQuery = query.value
        val movieCategorySelected = selectedCategory.value
        val getMostPopularMovies = getMovieCategory(MovieCategory.GET_MOST_POPULAR_MOVIES.value)
        val getUpcomingMovies = getMovieCategory(MovieCategory.GET_UPCOMING_MOVIES.value)
        val getTopRatedMovies = getMovieCategory(MovieCategory.GET_TOP_RATED_MOVIES.value)

        if(movieCategorySelected == getMostPopularMovies && textViewQuery == movieCategorySelected?.value){
            getMostPopularMovies()
        } else if(movieCategorySelected == getUpcomingMovies && textViewQuery == movieCategorySelected?.value){
            getUpcomingMovies()
        } else if(movieCategorySelected == getTopRatedMovies && textViewQuery == movieCategorySelected?.value){
            getTopRatedMovies()
        } else if(textViewQuery != movieCategorySelected?.value){
            newSearch()
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

    /**
     * Called when Icon in SearchAppBar is clicked
     * This will reset search state. if this doesn't work then i need to make a separate function that
     * at least will clear the search query
     */
    fun resetForNextSearch(){
        query.value = ""
        movies.value = listOf()
        page.value = 1
        onChangeMovieScrollPosition(0)
        if(selectedCategory.value?.value != query.value) clearSelectedCategory()
    }
}