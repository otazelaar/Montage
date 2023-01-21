package com.otaz.imdbmovieapp.network.responses

import com.google.gson.annotations.SerializedName
import com.otaz.imdbmovieapp.network.model.PosterDto


/**
 * Backdrop has the same data structure as Poster Dto. I am unsure if this is good practice
 * but I used Poster Dto for that for now. The backdrops do have different IDs
 */

data class PosterResponse(
    @SerializedName("imDbId") var id: String,
    @SerializedName("posters") var posters: List<PosterDto>,
//    @SerializedName("backdrop") var backdrop: List<PosterDto>,
    @SerializedName("errorMessage") var errorMessage: String,
)