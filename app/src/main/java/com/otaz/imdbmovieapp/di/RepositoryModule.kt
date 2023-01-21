package com.otaz.imdbmovieapp.di

import com.otaz.imdbmovieapp.network.MovieService
import com.otaz.imdbmovieapp.network.model.MovieDtoMapper
import com.otaz.imdbmovieapp.network.model.PosterDtoMapper
import com.otaz.imdbmovieapp.repository.MovieRepository
import com.otaz.imdbmovieapp.repository.MovieRepositoryImpl
import com.otaz.imdbmovieapp.repository.PosterRepository
import com.otaz.imdbmovieapp.repository.PosterRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * This Object provides Movie Repository dependency. This method was chosen over constructor injection in
 * MovieRepositoryImpl due to scalability and improved clarity of dependencies. Having many dependencies labeled in a
 * singular location such as RepositoryModule will make it clear on what dependencies are being used as the app grows.
 *
 * *** Also make comment on why the modules are broken up for testability ***
 */

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        movieService: MovieService,
        movieMapper: MovieDtoMapper,
    ): MovieRepository{
        return MovieRepositoryImpl(
            movieService = movieService,
            mapper = movieMapper
        )
    }

    @Singleton
    @Provides
    fun providePosterRepository(
        movieService: MovieService,
        posterMapper: PosterDtoMapper,
    ): PosterRepository {
        return PosterRepositoryImpl(
            movieService = movieService,
            mapper = posterMapper
        )
    }
}