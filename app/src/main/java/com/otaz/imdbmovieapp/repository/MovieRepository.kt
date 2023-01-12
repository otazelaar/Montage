package com.otaz.imdbmovieapp.repository

import com.otaz.imdbmovieapp.domain.model.Movie

interface MovieRepository{

    suspend fun search(apikey: String, expression: String): List<Movie>

    suspend fun get(apikey: String, id: String): Movie
}