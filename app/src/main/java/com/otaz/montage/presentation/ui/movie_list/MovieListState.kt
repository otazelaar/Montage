package com.otaz.montage.presentation.ui.movie_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.otaz.montage.domain.model.ImageConfigs
import com.otaz.montage.domain.model.Movie

data class MovieListState(
    val movie: List<Movie> = listOf(),
//    val cachedMovies: List<Movie> = listOf(), //this is for offline app functionality
    val savedMovies: List<Movie> = listOf(),
    val configurations: ImageConfigs = ImageConfigs.EMPTY_CONFIGURATIONS,
    val selectedCategory: MutableState<MovieCategory?> = mutableStateOf(null),
    val page: MutableState<Int> = mutableStateOf(1),
    val loading: MutableState<Boolean> = mutableStateOf(false),
    // make an error state here for telling the UI that there was error
)