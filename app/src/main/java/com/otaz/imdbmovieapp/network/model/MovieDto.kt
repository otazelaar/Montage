package com.otaz.imdbmovieapp.network.model

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("id") var id: String? = null,
    @SerializedName("imDbRating") var imdbRating: String? = null,
    @SerializedName("image") var imageURL: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("plot") var description: String? = null,
)