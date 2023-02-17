package com.otaz.imdbmovieapp.network.model

import com.google.gson.annotations.SerializedName

data class ImageConfigurationsDto(
    @SerializedName("backdrop_sizes") var backdrop_sizes: List<String>,
    @SerializedName("base_url") var base_url: String,
    @SerializedName("logo_sizes") var logo_sizes: List<String>,
    @SerializedName("poster_sizes") var poster_sizes: List<String>,
    @SerializedName("profile_sizes") var profile_sizes: List<String>,
    @SerializedName("secure_base_url") var secure_base_url: String,
    @SerializedName("still_sizes") var still_sizes: List<String>
)