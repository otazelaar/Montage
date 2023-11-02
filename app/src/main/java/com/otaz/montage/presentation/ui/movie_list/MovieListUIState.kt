package com.otaz.montage.presentation.ui.movie_list

import com.otaz.montage.domain.model.ImageConfigs
import com.otaz.montage.domain.model.Movie

data class MovieListUIState(
    val movie: List<Movie> = listOf(),
    val configurations: ImageConfigs = ImageConfigs.EMPTY_CONFIGURATIONS,
)