package com.otaz.montage.presentation.ui.movie_list

import android.util.Log
import androidx.compose.runtime.*
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
import kotlinx.coroutines.flow.*
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

//    private var _configurationsState = MutableStateFlow(EMPTY_CONFIGURATIONS)
//    val configurationsState: StateFlow<ImageConfigs> = _configurationsState.asStateFlow()

    // Attempting Jetpack compose UI State
//    var configurationsState by mutableStateOf(GetConfigurations.EMPTY_CONFIGURATIONS)
//        private set

    val configurations: MutableState<ImageConfigs> = mutableStateOf(GetConfigurations.EMPTY_CONFIGURATIONS)

    val query = mutableStateOf("")
    private val sortingParameterPopularityDescending = "popularity.desc"

    val selectedCategory: MutableState<MovieCategory?> = mutableStateOf(null)
    var categoryScrollPosition: Int = 0

    val loading = mutableStateOf(false)

    // Pagination
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
                    is NewSearch -> newSearchUseCasePicker()
                    is NextPage -> nextPage()
                    is MovieListEvent.SaveMovie -> saveMovie(movie = event.movie)
                }
            }catch (e: Exception){
                Log.e(TAG, "MovieListViewModel: onTriggerEvent: Exception ${e}, ${e.cause}")
            }
        }
    }

    private fun newSearch(){
        Log.d(TAG, "MovieListViewModel: newSearch: query: ${query.value}, page: ${page.value}")

        resetSearchState()
        searchMovies.execute(
            apikey = apiKey,
            query = query.value,
            page = page.value
        ).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let { list -> movies.value = list }
            dataState.error?.let { error -> Log.e(TAG,"MovieListViewModel: newSearch: Error:")}
        }.launchIn(viewModelScope)
    }

    private fun getMostPopularMovies(){
        Log.d(TAG, "MovieListViewModel: getMostPopularMovies: query: ${query.value}, page: ${page.value}")

        resetSearchState()

        // Makes the "Popular" category chip selected upon launching the application
        if(selectedCategory.value != MovieCategory.GET_MOST_POPULAR_MOVIES){
            selectedCategory.value = MovieCategory.GET_MOST_POPULAR_MOVIES
        }

        getMostPopularMovies.execute(
            apikey = apiKey,
            sortBy = sortingParameterPopularityDescending,
            page = page.value
        ).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let { list -> movies.value = list }
            dataState.error?.let { error -> Log.e(TAG,"MovieListViewModel: getMostPopularMovies: Error:")}
        }.launchIn(viewModelScope)
    }

    private fun getUpcomingMovies(){
        Log.d(TAG, "MovieListViewModel: getUpcomingMovies: query: ${query.value}, page: ${page.value}")

        resetSearchState()
        getUpcomingMovies.execute(
            apikey = apiKey,
            page = page.value
        ).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let { list -> movies.value = list }
            dataState.error?.let { error -> Log.e(TAG,"MovieListViewModel: getUpcomingMovies: Error:")}
        }.launchIn(viewModelScope)
    }

    private fun getTopRatedMovies(){
        Log.d(TAG, "MovieListViewModel: getTopRatedMovies: query: ${query.value}, page: ${page.value}")

        resetSearchState()
        getTopRatedMovies.execute(
            apikey = apiKey,
            page = page.value
        ).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let { list -> movies.value = list }
            dataState.error?.let { error -> Log.e(TAG,"MovieListViewModel: getTopRatedMovies: Error:")}
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
                    dataState.error?.let { error -> Log.e(TAG,"MovieListViewModel: Error") }
                }.launchIn(viewModelScope)
            }
        }
    }

    private fun getConfigurations(){
        Log.d(TAG, "MovieListViewModel: getConfigurations running")

        getConfigurations.execute(
            apikey = apiKey,
        ).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let { value -> configurations.value = value }
            dataState.error?.let { error -> Log.e(TAG,"MovieListViewModel: GetConfigurations: $error")}
        }.catch {
            // This is an example of how to catch errors off of the suspend function.
            // Should we handle errors in the use case or here?
            cause ->  Log.e(TAG, "$cause")
        }.launchIn(viewModelScope)
    }

    private suspend fun saveMovie(movie: Movie){
        Log.d(TAG, "MovieListViewModel: saveMovie running")
        saveMovie.execute(
            movie = movie
        )
    }

    /**
     * Append new movies to the current list of movies.
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
     * Called when a new search is executed and when Icon in SearchAppBar is clicked
     * This will reset search state. if this doesn't work then i need to make a separate function that
     * at least will clear the search query
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
        // might need to reset index here as well

//        not sure if this is working. I believe that query.value being changed is actually
//        resulting in the category being unselected.
        if(selectedCategory.value?.value != query.value) clearSelectedCategory()
    }
}