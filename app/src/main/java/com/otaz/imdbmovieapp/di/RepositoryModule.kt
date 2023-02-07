package com.otaz.imdbmovieapp.di

import com.otaz.imdbmovieapp.network.MovieService
import com.otaz.imdbmovieapp.network.model.MovieDtoMapper
import com.otaz.imdbmovieapp.network.model.MovieSpecDtoMapper
import com.otaz.imdbmovieapp.repository.MovieRepository
import com.otaz.imdbmovieapp.repository.MovieRepositoryImpl
import com.otaz.imdbmovieapp.repository.MovieSpecRepository
import com.otaz.imdbmovieapp.repository.MovieSpecRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        movieService: MovieService,
        movieMapper: MovieDtoMapper,
    ): MovieRepository {
        return MovieRepositoryImpl(
            movieService = movieService,
            mapper = movieMapper,
        )
    }

    @Singleton
    @Provides
    fun provideMovieSpecRepository(
        movieService: MovieService,
        movieMapper: MovieSpecDtoMapper,
    ): MovieSpecRepository {
        return MovieSpecRepositoryImpl(
            movieService = movieService,
            mapper = movieMapper,
        )
    }
}