package com.otaz.imdbmovieapp.network.responses

import com.google.gson.annotations.SerializedName
import com.otaz.imdbmovieapp.network.model.MovieDto

data class MovieSearchResponse(
    @SerializedName("Search") var movies: List<MovieDto>,
    @SerializedName("Response") var errorMessage: String,
    @SerializedName("totalResults") var totalResults: String,
)