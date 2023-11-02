package com.otaz.montage.presentation.ui.movie_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.otaz.montage.domain.model.ImageConfigs
import com.otaz.montage.domain.model.Movie

data class MovieListState(
    val movie: List<Movie> = listOf(),
    val configurations: ImageConfigs = ImageConfigs.EMPTY_CONFIGURATIONS,
    val selectedCategory: MutableState<MovieCategory?> = mutableStateOf(null),
    val page: MutableState<Int> = mutableStateOf(1),

)