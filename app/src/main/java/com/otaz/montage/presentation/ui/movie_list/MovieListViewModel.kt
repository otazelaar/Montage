package com.otaz.montage.presentation.ui.movie_list

import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaz.montage.domain.model.Movie
import com.otaz.montage.interactors.app.*
import com.otaz.montage.interactors.app.SaveMovie
import com.otaz.montage.interactors.movie_list.GetMostPopularMovies
import com.otaz.montage.interactors.movie_list.GetTopRatedMovies
import com.otaz.montage.interactors.movie_list.GetUpcomingMovies
import com.otaz.montage.interactors.movie_list.SearchMovies
import com.otaz.montage.presentation.ConnectivityManager
import com.otaz.montage.presentation.ui.movie_list.MovieListActions.*
import com.otaz.montage.util.MOVIE_PAGINATION_PAGE_SIZE
import com.otaz.montage.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import kotlin.collections.ArrayList

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val cacheMovie: CacheMovie,
    private val searchMovies: SearchMovies,
    private val getConfigurations: GetConfigurations,
    private val getMostPopularMovies: GetMostPopularMovies,
    private val getUpcomingMovies: GetUpcomingMovies,
    private val getTopRatedMovies: GetTopRatedMovies,
    private val getSavedMovies: GetSavedMovies,
    private val deleteMovie: DeleteMovie,
    private val saveMovie: SaveMovie,
    private val connectivityManager: ConnectivityManager,
    @Named("tmdb_apikey") private val apiKey: String,
    ): ViewModel() {
    val state: MutableState<MovieListState> = mutableStateOf(MovieListState())
    val query = mutableStateOf("")
    private val sortingParameterPopularityDescending = "popularity.desc"
    private var movieListScrollPosition = 0

    // might need to save the order added to the database so that each time the application is closed
    // we can still access the last state of the [orderAdded]
    // private val orderAdded: MutableState<Int> = mutableStateOf(0)

    init {
        getConfigurations()
        getMostPopularMovies()
    }

    // Differentiate between saved movies and cached movies. Saved movies are for the watchlist.
    // Cached movies are for offline app functionality
    fun actions(action: MovieListActions){
        viewModelScope.launch {
            try {
                when(action){
                    is NewSearch -> newSearchUseCasePicker()
                    is NextPage -> nextPage()
                    is CacheMovieAction-> {
                        cacheMovie(movie = action.movie)
                    }
                    is ResetForNewSearch -> resetForNewSearch()
                    is CategoryChanged -> onSelectedCategoryChanged(category = action.category)
                    is QueryChanged -> onQueryChanged(query = action.query)
                    is MovieScrollPositionChanged -> onChangeMovieScrollPosition(position = action.position)
                    is DeleteSavedMovie -> deleteSavedMovie(action.id)
                    is GetAllSavedMovies -> getSavedMoviesList() // needs to be changed to get WatchList
                    is SaveMovieToWatchlist -> {
                        val currentTime = Calendar.getInstance().time.toString()
                        saveMovie(action.movie, currentTime)
                        getSavedMoviesList()
                    }
                }
            }catch (e: Exception){
                Log.e(TAG, "MovieListViewModel: actions: Exception ${e}, ${e.cause}")
            }
        }
    }

//    fun events(event: MovieListEvents){
//        viewModelScope.launch {
//            try {
//                when(event){
//                    is MovieSavedButtonClicked -> ??
//                }
//            }catch (e: Exception){
//                Log.e(TAG, "MovieListViewModel: onTriggerEvent: Exception ${e}, ${e.cause}")
//            }
//        }
//    }

    private fun newSearch(){
        Log.d(TAG, "MovieListViewModel: newSearch: query: ${query.value}, page: ${state.value.page.value}")

        resetSearchState()
        searchMovies.execute(
            connectivityManager, apiKey, query.value, state.value.page.value
        ).onEach { dataState ->
            state.value.loading.value = dataState.loading
            dataState.data?.let { list -> state.value = state.value.copy(movie = list) }
            dataState.error?.let { error -> Log.e(TAG,"MovieListViewModel: newSearch: Error:")
                // show error to UI from here
                // state.value.error
            }
        }.launchIn(viewModelScope)
    }

    private fun getMostPopularMovies(){
        Log.d(TAG, "MovieListViewModel: getMostPopularMovies: query: ${query.value}, page: ${state.value.page.value}")

        resetSearchState()

        // Makes the "Popular" category chip selected upon launching the application
        if(state.value.selectedCategory.value != MovieCategory.GET_MOST_POPULAR_MOVIES){
            state.value.selectedCategory.value = MovieCategory.GET_MOST_POPULAR_MOVIES
        }

        getMostPopularMovies.execute(
            apiKey, sortingParameterPopularityDescending, state.value.page.value
        ).onEach { dataState ->
            state.value.loading.value = dataState.loading
            dataState.data?.let { list -> state.value = state.value.copy(movie = list) }
            dataState.error?.let { error -> Log.e(TAG,"MovieListViewModel: getMostPopularMovies: $error:")}
        }.launchIn(viewModelScope)
    }

    private fun getUpcomingMovies(){
        Log.d(TAG, "MovieListViewModel: getUpcomingMovies: query: ${query.value}, page: ${state.value.page.value}")

        resetSearchState()
        getUpcomingMovies.execute(
            apiKey, state.value.page.value
        ).onEach { dataState ->
            state.value.loading.value = dataState.loading
            dataState.data?.let { list -> state.value = state.value.copy(movie = list) }
            dataState.error?.let { error -> Log.e(TAG,"MovieListViewModel: getUpcomingMovies: Error:")}
        }.launchIn(viewModelScope)
    }

    private fun getTopRatedMovies(){
        Log.d(TAG, "MovieListViewModel: getTopRatedMovies: query: ${query.value}, page: ${state.value.page.value}")

        resetSearchState()
        getTopRatedMovies.execute(
            apiKey, state.value.page.value
        ).onEach { dataState ->
            state.value.loading.value = dataState.loading
            dataState.data?.let { list -> state.value = state.value.copy(movie = list) }
            dataState.error?.let { error -> Log.e(TAG,"MovieListViewModel: getTopRatedMovies: Error:")}
        }.launchIn(viewModelScope)
    }

    private fun nextPage(){
        if((movieListScrollPosition + 1) >= (state.value.page.value * MOVIE_PAGINATION_PAGE_SIZE)){
            incrementPage()
            Log.d(TAG, "MovieListViewModel: nextPage: triggered: ${state.value.page.value}")

            if(state.value.page.value > 1){
                searchMovies.execute(
                    connectivityManager, apiKey, query.value, state.value.page.value,
                ).onEach { dataState ->
                    state.value.loading.value = dataState.loading
                    dataState.data?.let { list -> appendMovies(list) }
                    dataState.error?.let { error -> Log.e(TAG,"MovieListViewModel: Error") }
                }.launchIn(viewModelScope)
            }
        }
    }

    private fun getConfigurations(){
        Log.d(TAG, "MovieListViewModel: getConfigurations running")

        getConfigurations.execute(
            apiKey,
        ).onEach { dataState ->
            state.value.loading.value = dataState.loading
            dataState.data?.let { imageConfigs -> state.value = state.value.copy(configurations = imageConfigs) }
            dataState.error?.let { error -> Log.e(TAG,"MovieListViewModel: GetConfigurations: $error")}
        }.catch {
            // This is an example of how to catch errors off of the suspend function.
            // Should we handle errors in the use case or here? Maybe bc we could then have VM
            // events to show a different screen or something like that. If it is in the UC then we
            // would not be able ot return those events
            cause ->  Log.e(TAG, "$cause")
        }.launchIn(viewModelScope)
    }

    private suspend fun cacheMovie(movie: Movie){
        Log.d(TAG, "MovieListViewModel: saveMovie running")
        cacheMovie.execute(
            movie = movie
        )
    }

    //add date as a parameter to the following function
    private suspend fun saveMovie(movie: Movie, currentTime: String){
        Log.d(TAG, "MovieListViewModel: addMovieToWatchList running")

        val movieItemAdjusted = movie.copy(
            isInWatchlist = true,
            timeSavedToWatchList = currentTime
        )

        saveMovie.execute(
            movie = movieItemAdjusted
        )
    }

    private fun getSavedMoviesList(){
        Log.d(TAG, "SavedMoviesListViewModel: getSavedMovies: running")

        getSavedMovies.execute().onEach { dataState ->
            state.value.loading.value = dataState.loading
            dataState.data?.let { list -> state.value = state.value.copy(savedMovies = list) }
            dataState.error?.let { error -> Log.e(TAG,"SavedMoviesListViewModel: getSavedMovies: Error:")}
        }.launchIn(viewModelScope)
    }

    private suspend fun deleteSavedMovie(id: String){

        // TODO() instead of deleting this movie we should just adjust the value to isInWatchlist to false and update/refresh/getSavedMoviesList()
        deleteMovie.execute(
            id = id
        )
        getSavedMoviesList()
    }

    /**
     * Append new movies to the current list of movies.
     */
    private fun appendMovies(movies: List<Movie>){
        val currentList = state.value.movie.let { ArrayList(it) }
        currentList.addAll(movies)
        state.value = state.value.copy(movie = currentList)
    }

    private fun incrementPage(){
        setPage(state.value.page.value + 1)
    }

    private fun onChangeMovieScrollPosition(position: Int){
        setListScrollPosition(position = position)
    }

    /**
     * Called when a new search is executed and when Icon in SearchAppBar is clicked
     * This will reset search state. if this doesn't work then i need to make a separate function that
     * at least will clear the search query
     */
    private fun resetSearchState(){
        // make sure to set state.value.copy = to state.value to assign the state the new copied value
        state.value = state.value.copy(movie = listOf())
        state.value.page.value = 1
        onChangeMovieScrollPosition(0)
        // might need to reset index here as well
        if(state.value.selectedCategory.value?.value != query.value) clearSelectedCategory()
    }

    private fun clearSelectedCategory(){
        setSelectedCategory(null)
        state.value.selectedCategory.value = null
    }

    private fun onQueryChanged(query: String){
        setQuery(query)
    }

    private fun onSelectedCategoryChanged(category: String){
        val newCategory = getMovieCategory(category)
        setSelectedCategory(newCategory)
        onQueryChanged(category)
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
        val movieCategorySelected = state.value.selectedCategory.value
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
        this.state.value.page.value = page
    }

    private fun setSelectedCategory(category: MovieCategory?){
        state.value.selectedCategory.value = category
    }

    private fun setQuery(query: String){
        this.query.value = query
    }

    /**
     * Called when Icon in SearchAppBar is clicked
     * This will reset search state. if this doesn't work then i need to make a separate function that
     * at least will clear the search query
     */
    private fun resetForNewSearch(){
        query.value = ""
        state.value = state.value.copy(movie = listOf())

        // I may need to use copy on the following line of code as I should not have mutable values in my UI state
        state.value.page.value = 1
        onChangeMovieScrollPosition(0)
        // might need to reset index here as well

//        not sure if this is working. I believe that query.value being changed is actually
//        resulting in the category being unselected.
        if(state.value.selectedCategory.value?.value != query.value) clearSelectedCategory()
    }
}