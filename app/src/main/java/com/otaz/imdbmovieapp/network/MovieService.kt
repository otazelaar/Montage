package com.otaz.imdbmovieapp.network

import com.otaz.imdbmovieapp.network.model.MovieDto
import com.otaz.imdbmovieapp.network.model.PosterDto
import com.otaz.imdbmovieapp.network.responses.MovieSearchResponse
import com.otaz.imdbmovieapp.network.responses.PosterResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    /**
     * Search for a movie using an expression
     */
    @GET("AdvancedSearch")
    suspend fun search(
        @Query("apikey") apikey: String,
        @Query("title") expression: String,
        @Query("count") count: String,
        ): MovieSearchResponse

    /**
     * Return a movie by its specific ID
     */
    @GET("Title")
    suspend fun get(
        @Query("apikey") apikey: String,
        @Query("id") id: String,
    ): MovieDto

    /**
     * Get movie or series poster using movie id
     */
    @GET("Posters")
    suspend fun poster(
        @Query("apikey") apikey: String,
        @Query("id") id: String,
    ): PosterResponse

}