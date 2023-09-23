package com.otaz.montage.network.model

import com.google.gson.annotations.SerializedName
import com.otaz.montage.domain.model.MovieReview

/**
 * This data class contains the author and the content of reviews posted to the TMDB API
 */

data class MovieReviewDto(
    @SerializedName("author") var author: String,
    @SerializedName("content") var content: String,
)

fun MovieReviewDto.toMovieReview(): MovieReview {
    return MovieReview(
        author = author,
        content = content,
    )
}