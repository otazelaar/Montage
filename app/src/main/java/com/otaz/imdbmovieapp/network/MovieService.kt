package com.otaz.imdbmovieapp.network

import com.otaz.imdbmovieapp.network.responses.ConfigurationResponse
import com.otaz.imdbmovieapp.network.responses.MovieResponse
import com.otaz.imdbmovieapp.network.responses.MovieSpecsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    /**
     * Search for a movie using a query
     */
    @GET("search/movie")
    suspend fun search(
        @Query("api_key") apikey: String,
        @Query("query", encoded = true) query: String,
    ): MovieResponse

    /**
     * Search for a movie using an id
     */
    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") id: Int,
        @Query("api_key") apikey: String,
    ): MovieSpecsDto

    /**
     *
     */
    @GET("configuration")
    suspend fun configuration(
        @Query("api_key") apikey: String,
    ): ConfigurationResponse
}