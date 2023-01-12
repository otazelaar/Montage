package com.otaz.imdbmovieapp.network.responses

import com.google.gson.annotations.SerializedName
import com.otaz.imdbmovieapp.network.model.MovieDto

data class MovieSearchResponse(
    @SerializedName("results")
    var movies: List<MovieDto>,
    @SerializedName("errorMessage")
    var errorMessage: String,
)