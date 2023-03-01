package com.otaz.imdbmovieapp.network.responses

import com.google.gson.annotations.SerializedName

data class MovieReviewsResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("page") var page: Int,
    @SerializedName("results") var movieReviewDtos: List<MovieReviewDto>,
    @SerializedName("total_pages") var total_pages: Int,
    @SerializedName("total_results") var total_results: Int
)