package com.otaz.montage.network.model

import com.google.gson.annotations.SerializedName

data class TmdbMovieSpecsDto(
    @SerializedName("backdrop_path") var backdrop_path: String?,
    @SerializedName("id") var id: Int,
    @SerializedName("imdb_id") var imdb_id: String?,
    @SerializedName("overview") var overview: String?,
    @SerializedName("title") var title: String,
)