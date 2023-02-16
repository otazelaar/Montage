package com.otaz.imdbmovieapp.cache

import com.otaz.imdbmovieapp.cache.model.MovieEntity
import com.otaz.imdbmovieapp.cache.model.MovieSpecsEntity

class AppDatabaseFake{
    val movies = mutableListOf<MovieEntity>()
    val movieSpecs = mutableListOf<MovieSpecsEntity>()
}