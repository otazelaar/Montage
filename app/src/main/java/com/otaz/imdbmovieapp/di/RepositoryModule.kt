package com.otaz.imdbmovieapp.di

import com.otaz.imdbmovieapp.network.MovieService
import com.otaz.imdbmovieapp.network.model.MovieDtoMapper
import com.otaz.imdbmovieapp.repository.MovieRepository
import com.otaz.imdbmovieapp.repository.MovieRepositoryImpl
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
            mapper = movieMapper
        )
    }
}