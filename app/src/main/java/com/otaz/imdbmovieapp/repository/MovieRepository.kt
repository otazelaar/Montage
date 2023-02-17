package com.otaz.imdbmovieapp.repository

import com.otaz.imdbmovieapp.domain.model.Movie

interface MovieRepository{
    suspend fun search(apikey: String, query: String): List<Movie>
}