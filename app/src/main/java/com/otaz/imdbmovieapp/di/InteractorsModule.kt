package com.otaz.imdbmovieapp.di

import com.otaz.imdbmovieapp.cache.model.MovieEntityMapper
import com.otaz.imdbmovieapp.interactors.movie_list.SearchMovies
import com.otaz.imdbmovieapp.network.MovieService
import com.otaz.imdbmovieapp.network.model.MovieDao
import com.otaz.imdbmovieapp.network.model.MovieDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object InteractorsModule {

    @ViewModelScoped
    @Provides
    fun provideMovieService(
        movieService: MovieService,
        movieDao: MovieDao,
        movieEntityMapper: MovieEntityMapper,
        movieDtoMapper: MovieDtoMapper,
    ): SearchMovies{
        return SearchMovies(
            movieService = movieService,
            movieDao = movieDao,
            entityMapper = movieEntityMapper,
            dtoMapper = movieDtoMapper,
        )
    }
}