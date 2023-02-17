package com.otaz.imdbmovieapp.network

import com.otaz.imdbmovieapp.network.responses.ConfigurationResponse
import com.otaz.imdbmovieapp.network.responses.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    /**
     * Search for a movie using an expression
     * Each page is 10 items
     */
    @GET("search/movie")
    suspend fun search(
        @Query("api_key") apikey: String,
        @Query("query", encoded = true) query: String,
    ): MovieResponse

    /**
     * Search for a movie using an expression
     * Each page is 10 items
     */
    @GET("configuration")
    suspend fun configuration(
        @Query("api_key") apikey: String,
    ): ConfigurationResponse
}