package com.otaz.imdbmovieapp.network.responses

import com.google.gson.annotations.SerializedName

data class MovieReviewDto(
    @SerializedName("author") var author: String,
    @SerializedName("content") var content: String,
)