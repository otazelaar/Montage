package com.otaz.imdbmovieapp.network.model

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("id") var id: Int,
    @SerializedName("adult") var adult: Boolean,
    @SerializedName("backdrop_path") var backdrop_path: String?,
    @SerializedName("overview") var overview: String,
    @SerializedName("poster_path") var poster_path: String?,
    @SerializedName("release_date") var release_date: String,
    @SerializedName("title") var title: String,
)