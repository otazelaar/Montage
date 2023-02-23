package com.otaz.imdbmovieapp.network

import com.otaz.imdbmovieapp.network.model.OmdbMovieSpecDto
import retrofit2.http.GET
import retrofit2.http.Query

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