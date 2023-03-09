package com.otaz.imdbmovieapp.network.model

import com.google.gson.annotations.SerializedName

/**
 * This data class contains the author and the content of reviews posted to the TMDB API
 */

data class MovieReviewDto(
    @SerializedName("author") var author: String,
    @SerializedName("content") var content: String,
)