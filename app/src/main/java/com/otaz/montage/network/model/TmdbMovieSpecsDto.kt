package com.otaz.montage.network.model

import com.google.gson.annotations.SerializedName

data class TmdbMovieSpecsDto(
    @SerializedName("adult") var adult: Boolean,
    @SerializedName("backdrop_path") var backdrop_path: String?,
    @SerializedName("budget") var budget: Int,
    @SerializedName("homepage") var homepage: String,
    @SerializedName("id") var id: Int,
    @SerializedName("imdb_id") var imdb_id: String?,
    @SerializedName("original_language") var original_language: String,
    @SerializedName("original_title") var original_title: String,
    @SerializedName("overview") var overview: String?,
    @SerializedName("popularity") var popularity: Double,
    @SerializedName("poster_path") var poster_path: String?,
    @SerializedName("release_date") var release_date: String,
    @SerializedName("revenue") var revenue: Long?,
    @SerializedName("runtime") var runtime: Int?,
    @SerializedName("status") var status: String,
    @SerializedName("tagline") var tagline: String?,
    @SerializedName("title") var title: String,
    @SerializedName("video") var video: Boolean,
    @SerializedName("vote_average") var vote_average: Double,
    @SerializedName("vote_count") var vote_count: Int
)