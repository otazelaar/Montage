package com.otaz.imdbmovieapp.network.model

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("imdbID") var id: String,
    @SerializedName("Year") var year: String?,
    @SerializedName("Poster") var imageURL: String,
    @SerializedName("Title") var title: String,
    @SerializedName("Type") var type: String?,
)

