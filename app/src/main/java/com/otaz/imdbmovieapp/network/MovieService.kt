package com.otaz.imdbmovieapp.network

import com.otaz.imdbmovieapp.network.model.MovieDto
import com.otaz.imdbmovieapp.network.responses.MovieSearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MovieService {

    /**
     * Search for a movie using an expression
     */
    @GET("SearchMovie")
    suspend fun search(
        @Query("apikey") apikey: String,
        @Query("query") query: String,
    ): MovieSearchResponse

    /**
     * Return a movie by its specific ID
     */
    @GET("Title")
    suspend fun get(
        @Header("apikey") apikey: String,
        @Query("id") id: String,
    ): MovieDto

}