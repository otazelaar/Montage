package com.otaz.imdbmovieapp.network.responses

import com.google.gson.annotations.SerializedName
import com.otaz.imdbmovieapp.network.model.MovieReviewDto

/**
 * This response contains a list of movie reviews posted by users to the TMDB API
 */

data class MovieReviewsResponse(
    @SerializedName("results") var movieReviewDtos: List<MovieReviewDto>,
)