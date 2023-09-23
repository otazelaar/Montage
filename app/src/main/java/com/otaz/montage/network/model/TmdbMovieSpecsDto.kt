package com.otaz.montage.network.model

import com.google.gson.annotations.SerializedName
import com.otaz.montage.domain.model.TmdbMovieSpecs

data class TmdbMovieSpecsDto(
    @SerializedName("adult") var adult: Boolean,
    @SerializedName("backdrop_path") var backdrop_path: String?,
    @SerializedName("budget") var budget: Int,
    @SerializedName("id") var id: Int,
    @SerializedName("imdb_id") var imdb_id: String?,
    @SerializedName("original_title") var original_title: String,
    @SerializedName("overview") var overview: String?,
    @SerializedName("popularity") var popularity: Double,
    @SerializedName("poster_path") var poster_path: String?,
    @SerializedName("release_date") var release_date: String,
    @SerializedName("revenue") var revenue: Long?,
    @SerializedName("runtime") var runtime: Int?,
    @SerializedName("title") var title: String,
    @SerializedName("vote_average") var vote_average: Double,
    @SerializedName("vote_count") var vote_count: Int
)

/**
 * Maps the data transfer object to the domain where it is the data is
 * used as the core business logic for the application.
 */
fun TmdbMovieSpecsDto.toTmdbMovieSpecs(): TmdbMovieSpecs {
    return TmdbMovieSpecs(
        adult = adult,
        backdrop_path = backdrop_path,
        budget = budget,
        id = id,
        imdb_id = imdb_id,
        original_title = original_title,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        revenue = revenue,
        runtime = runtime,
        title = title,
        vote_average = vote_average,
        vote_count = vote_count,
    )
}