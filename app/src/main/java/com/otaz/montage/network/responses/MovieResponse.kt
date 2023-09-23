package com.otaz.montage.network.responses

import com.google.gson.annotations.SerializedName
import com.otaz.montage.network.model.MovieDto

data class MovieResponse(
    @SerializedName("results") var moviesDto: List<MovieDto>,
)