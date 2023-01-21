package com.otaz.imdbmovieapp.repository

import com.otaz.imdbmovieapp.domain.model.Movie
import com.otaz.imdbmovieapp.domain.model.Poster

interface PosterRepository {

    suspend fun poster(apikey: String, id: String): List<Poster>

}