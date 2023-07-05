package com.otaz.montage.network

import com.otaz.montage.network.model.OmdbMovieSpecDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * This interface defines OMDB API calls using a suspend function. This secondary API is necessary
 * to acquire specific movie ratings as these were not present in the TMDB API.
 *
 * These ratings include IMDB and Metacritic.
 */

interface OmdbApiService {

    /**
     * Search for a movie using an IMDB ID
     */
    @GET(".")
    suspend fun get(
        @Query("apikey") apikey: String,
        @Query("i") id: String,
    ): OmdbMovieSpecDto
}