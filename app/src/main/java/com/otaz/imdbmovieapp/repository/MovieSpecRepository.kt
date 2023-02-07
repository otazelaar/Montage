package com.otaz.imdbmovieapp.repository

import com.otaz.imdbmovieapp.domain.model.MovieSpecs

interface MovieSpecRepository{

    suspend fun get(apiKey: String, id: String): MovieSpecs
}