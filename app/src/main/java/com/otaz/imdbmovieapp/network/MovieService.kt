package com.otaz.imdbmovieapp.network

import com.otaz.imdbmovieapp.network.model.MovieSpecDto
import com.otaz.imdbmovieapp.network.responses.MovieSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    /**
     * Search for a movie using an expression
     * Each page is 10 items
     */
    @GET(".")
    suspend fun search(
        @Query("apikey") apikey: String,
        @Query("s") query: String,
        @Query("page") page: String,
    ): MovieSearchResponse

    @GET(".")
    suspend fun get(
        @Query("apikey") apikey: String,
        @Query("i") id: String,
    ): MovieSpecDto
}