package com.otaz.imdbmovieapp.network.model

import com.google.gson.annotations.SerializedName

data class PosterDto(
    @SerializedName("id") var id: String,
    @SerializedName("link") var link: String,
    @SerializedName("aspectRatio") var aspectRatio: Int,
    @SerializedName("language") var language: String,
    @SerializedName("width") var width: Int,
    @SerializedName("height") var height: Int,
)