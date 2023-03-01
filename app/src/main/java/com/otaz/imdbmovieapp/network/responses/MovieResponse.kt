package com.otaz.imdbmovieapp.network.responses

import com.google.gson.annotations.SerializedName
import com.otaz.imdbmovieapp.network.model.MovieDto

data class MovieResponse(
    @SerializedName("page") var page: Int,
    @SerializedName("results") var movies: List<MovieDto>,
    @SerializedName("total_pages") var total_pages: Int,
    @SerializedName("total_results") var total_results: Int
)