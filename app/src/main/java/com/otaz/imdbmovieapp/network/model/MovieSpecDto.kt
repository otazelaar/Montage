package com.otaz.imdbmovieapp.network.model

import com.google.gson.annotations.SerializedName

data class MovieSpecDto(
    @SerializedName("Actors") var actors: String,
    @SerializedName("Awards") var awards: String,
    @SerializedName("BoxOffice") var boxOffice: String,
    @SerializedName("Country") var country: String,
    @SerializedName("DVD") var dvd: String,
    @SerializedName("Director") var director: String,
    @SerializedName("Genre") var genre: String,
    @SerializedName("Language") var language: String,
    @SerializedName("Metascore") var metascore: String,
    @SerializedName("Plot") var plot: String,
    @SerializedName("Poster") var poster: String,
    @SerializedName("Production") var production: String,
    @SerializedName("Rated") var rated: String,
    @SerializedName("Released") var released: String,
    @SerializedName("Response") var response: String,
    @SerializedName("Runtime") var runtime: String,
    @SerializedName("Title") var title: String,
    @SerializedName("Type") var type: String,
    @SerializedName("Website") var website: String,
    @SerializedName("Writer") var writer: String,
    @SerializedName("Year") var year: String,
    @SerializedName("imdbID") var imdbID: String,
    @SerializedName("imdbRating") var imdbRating: String,
    @SerializedName("imdbVotes") var imdbVotes: String
)