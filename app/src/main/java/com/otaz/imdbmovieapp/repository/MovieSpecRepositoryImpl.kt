//package com.otaz.imdbmovieapp.repository
//
//import com.otaz.imdbmovieapp.network.MovieService
//
//class MovieSpecRepositoryImpl(
//    private val movieService: MovieService,
//    private val mapper: MovieSpecDtoMapper,
//): MovieSpecRepository {
//    override suspend fun get(apiKey: String, id: String): MovieSpecs {
//        val result = movieService.get(apikey = apiKey, id = id)
//        return mapper.mapToDomainModel(result)
//    }
//}