package com.otaz.imdbmovieapp.network.model

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("id") var id: String,
    @SerializedName("resultType") var resultType: String,
    @SerializedName("image") var imageURL: String,
    @SerializedName("title") var title: String,
    @SerializedName("description") var description: String,
)