package com.otaz.imdbmovieapp.network.model

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("crew")
    var crew: String? = null,
    @SerializedName("fullTitle")
    var fullTitle: String? = null,
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("imDbRating")
    var imDbRating: String? = null,
    @SerializedName("imDbRatingCount")
    var imDbRatingCount: String? = null,
    @SerializedName("image")
    var image: String? = null,
    @SerializedName("rank")
    var rank: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("year")
    var year: String? = null,
)