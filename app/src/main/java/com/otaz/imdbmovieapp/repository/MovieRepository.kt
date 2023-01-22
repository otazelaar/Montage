package com.otaz.imdbmovieapp.repository

import com.otaz.imdbmovieapp.domain.model.Movie

interface MovieRepository{

    suspend fun search(apikey: String, expression: String, count: String): List<Movie>

    suspend fun get(apikey: String, id: String): Movie
}