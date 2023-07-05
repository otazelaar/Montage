package com.otaz.montage.network

import com.otaz.montage.network.responses.ConfigurationResponse
import com.otaz.montage.network.responses.MovieResponse
import com.otaz.montage.network.model.TmdbMovieSpecsDto
import com.otaz.montage.network.responses.MovieReviewsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * This interface defines TMDB API calls using suspend functions
 */

interface TmdbApiService {

    /**
     * Search for a movie using a query
     */
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apikey: String,
        @Query("query", encoded = true) query: String,
        @Query("page") page: Int,
    ): MovieResponse

    /**
     * Search for a movie using a query
     */
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apikey: String,
        @Query("page") page: Int,
    ): MovieResponse

    /**
     * Search for a movie using a query
     */
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apikey: String,
        @Query("page") page: Int,
    ): MovieResponse

    /**
     * Discover movies using a variety of different parameters
     */
    @GET("discover/movie")
    suspend fun getMostPopularMovies(
        @Query("api_key") apikey: String,
        @Query("sort_by") sortBy: String,
        @Query("page") page: Int,
    ): MovieResponse

    /**
     * Search for a movie using an id
     */
    @GET("movie/{movie_id}")
    suspend fun getMovieSpecs(
        @Path("movie_id") id: Int,
        @Query("api_key") apikey: String,
    ): TmdbMovieSpecsDto

    /**
     * Search for the review of a movie using an TMDB ID
     */
    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") id: Int,
        @Query("api_key") apikey: String,
        @Query("page") page: Int,
    ): MovieReviewsResponse

    /**
     *
     */
    @GET("configuration")
    suspend fun configuration(
        @Query("api_key") apikey: String,
    ): ConfigurationResponse
}