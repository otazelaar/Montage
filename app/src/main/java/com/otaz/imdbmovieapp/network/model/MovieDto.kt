package com.otaz.imdbmovieapp.network.model

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("id") var id: String,
    @SerializedName("imDbRating") var imdbRating: String?,
    @SerializedName("image") var imageURL: String,
    @SerializedName("title") var title: String,
    @SerializedName("plot") var description: String?,
//    @SerializedName("keywordList") var keywords: List<String> = emptyList(),
)