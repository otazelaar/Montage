package com.otaz.imdbmovieapp.presentation.ui.movie_detail

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaz.imdbmovieapp.domain.model.*
import com.otaz.imdbmovieapp.interactors.app.GetConfigurations
import com.otaz.imdbmovieapp.interactors.movie_detail.GetMovieReviews
import com.otaz.imdbmovieapp.interactors.movie_detail.GetTmdbMovie
import com.otaz.imdbmovieapp.interactors.movie_detail.GetOmdbMovie
import com.otaz.imdbmovieapp.presentation.ui.movie_detail.MovieEvent.*
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
class MovieDetailViewModel @Inject constructor(
    private val getMovieReviews: GetMovieReviews,
    private val getTmdbMovie: GetTmdbMovie,
    private val getOmdbMovie: GetOmdbMovie,
    private val getConfigurations: GetConfigurations,
    @Named("tmdb_apikey") private val tmdb_apiKey: String,
    @Named("omdb_apikey") private val omdb_apiKey: String,
    ): ViewModel(){

    val onLoad: MutableState<Boolean> = mutableStateOf(false)
    val movieTmdb: MutableState<TmdbMovieSpecs?> = mutableStateOf(null)
    val movieOmdb: MutableState<OmdbMovieSpecs?> = mutableStateOf(null)
    val reviews: MutableState<List<MovieReview>> = mutableStateOf(ArrayList())
    val configurations: MutableState<ImageConfigs> = mutableStateOf(GetConfigurations.EMPTY_CONFIGURATIONS)

    val loading = mutableStateOf(false)
    val dialogQueue = DialogQueue()

    // Paging
    val page = mutableStateOf(1)
    var reviewListScrollPosition = 0

    init {
        getConfigurations()
    }

    fun onTriggerEvent(event: MovieEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is GetTmdbMovieEvent -> {
                        if(movieTmdb.value == null){
                            this@MovieDetailViewModel.getTmdbMovie(event.tmdb_id)
                        }
                    }
                    is NextPageEvent -> {
                        // This can be called here because the id is already gotten by the time we get to the next page
                        movieTmdb.value?.let { nextPage(it.id) }
                    }
                }
            }catch (e: Exception){
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
        }
    }

    private fun getTmdbMovie(id: Int){
        getTmdbMovie.execute(
            tmdb_apikey = tmdb_apiKey,
            id = id,
        ).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let {
                data -> movieTmdb.value = data

                // call for OMDB movie using imdb ID --> This gets us ratings for IMDB and Metacritic
                data.imdb_id?.let { getOmdbMovie(it) }
                Log.i(TAG, "getTmdbMovie: Success: ${data.id}")

                // call for movie reviews
                getMovieReviews(data.id)
                Log.i(TAG, "getMovieReviews: Success: ${data.id} & ${reviews.value}")

            }
            dataState.error?.let { error -> dialogQueue.appendErrorMessage("getTmdbMovie: Error", error) }
        }.launchIn(viewModelScope)
    }

    private fun getOmdbMovie(omdb_id: String){
        getOmdbMovie.execute(
            omdb_apikey = omdb_apiKey,
            omdb_id = omdb_id,
        ).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let {
                data -> movieOmdb.value = data
                Log.i(TAG, "getOmdbMovie: Success: ${data.id}")
            }
            dataState.error?.let { error -> dialogQueue.appendErrorMessage("getOmdbMovie: Error", error) }
        }.launchIn(viewModelScope)
    }

    private fun getConfigurations(){
        Log.d(TAG, "getConfigurations running")

        getConfigurations.execute(
            apikey = tmdb_apiKey,
        ).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let { value -> configurations.value = value }
            dataState.error?.let { error -> dialogQueue.appendErrorMessage("GetConfigurations Error", error)}
        }.launchIn(viewModelScope)
    }

    private fun getMovieReviews(id: Int){
        Log.d(TAG, "getMovieReviews: id: ${reviews.value}")

        resetSearchState()
        getMovieReviews.execute(
            id = id,
            apikey = tmdb_apiKey,
            page = page.value
        ).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let { list -> reviews.value = list }
            dataState.error?.let { error -> dialogQueue.appendErrorMessage("getMovieReviews: Error:", error)}
        }.launchIn(viewModelScope)
    }

    private fun nextPage(id: Int){
        if((reviewListScrollPosition + 1) >= (page.value * MOVIE_PAGINATION_PAGE_SIZE)){
            incrementPage()
            Log.d(TAG, "nextPageReviews: triggered: ${page.value}")

            if(page.value > 1){
                getMovieReviews.execute(
                    id = id,
                    apikey = tmdb_apiKey,
                    page = page.value
                ).onEach { dataState ->
                    loading.value = dataState.loading
                    dataState.data?.let { list -> appendMovies(list) }
                    dataState.error?.let { error -> dialogQueue.appendErrorMessage("Error", error) }
                }.launchIn(viewModelScope)
            }
        }
    }

    /**
     * Called when a getMovieReviews is executed
     */
    private fun resetSearchState(){
        reviews.value = listOf()
        page.value = 1
        onChangeReviewScrollPosition(0)
    }

    fun onChangeReviewScrollPosition(position: Int){
        setListScrollPosition(position = position)
    }

    private fun setListScrollPosition(position: Int){
        reviewListScrollPosition = position
    }

    private fun incrementPage(){
        setPage(page.value + 1)
    }

    private fun setPage(page: Int){
        this.page.value = page
    }

    private fun appendMovies(reviews: List<MovieReview>){
        val currentList = ArrayList(this.reviews.value)
        currentList.addAll(reviews)
        this.reviews.value = currentList
    }
}