package com.otaz.montage.network.model

import com.google.gson.annotations.SerializedName

data class OmdbMovieSpecDto(
    @SerializedName("imdbID") var imdbID: String?,
    @SerializedName("Director") var director: String?,
    @SerializedName("Metascore") var metascore: String?,
    @SerializedName("Released") var released: String?,
    @SerializedName("Runtime") var runtime: String?,
    @SerializedName("Year") var year: String?,
    @SerializedName("imdbRating") var imdbRating: String?,
)