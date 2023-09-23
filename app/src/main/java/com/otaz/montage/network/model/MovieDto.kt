package com.otaz.montage.network.model

import com.google.gson.annotations.SerializedName
import com.otaz.montage.domain.model.Movie

data class MovieDto(
    @SerializedName("id") var id: Int,
    @SerializedName("adult") var adult: Boolean,
    @SerializedName("backdrop_path") var backdrop_path: String?,
    @SerializedName("overview") var overview: String,
    @SerializedName("poster_path") var poster_path: String?,
    @SerializedName("release_date") var release_date: String?,
    @SerializedName("title") var title: String,
)

/**
 * Maps the data transfer object to the domain where it is the data is
 * used as the core business logic for the application.
 */
fun MovieDto.toMovie(): Movie {
    return Movie(
        id = id,
        adult = adult,
        backdrop_path = backdrop_path,
        overview = overview,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
    )
}