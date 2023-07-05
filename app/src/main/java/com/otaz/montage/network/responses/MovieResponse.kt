package com.otaz.montage.network.responses

import com.google.gson.annotations.SerializedName
import com.otaz.montage.network.model.MovieDto

/**
 * This response contains a list of movies
 */

data class MovieResponse(
    @SerializedName("results") var movies: List<MovieDto>,
)