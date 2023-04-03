package com.otaz.imdbmovieapp.network.responses

import com.google.gson.annotations.SerializedName
import com.otaz.imdbmovieapp.network.model.MovieDto

/**
 * This response contains a list of movies
 */

data class MovieResponse(
    @SerializedName("results") var movies: List<MovieDto>,
)