package com.otaz.imdbmovieapp.di

import com.otaz.imdbmovieapp.cache.model.MovieEntityMapper
import com.otaz.imdbmovieapp.cache.model.MovieSpecEntityMapper
import com.otaz.imdbmovieapp.interactors.movie.GetMovie
import com.otaz.imdbmovieapp.interactors.movie_list.RestoreMovies
import com.otaz.imdbmovieapp.interactors.movie_list.SearchMovies
import com.otaz.imdbmovieapp.network.MovieService
import com.otaz.imdbmovieapp.network.model.MovieDao
import com.otaz.imdbmovieapp.network.model.MovieDtoMapper
import com.otaz.imdbmovieapp.network.model.MovieSpecDao
import com.otaz.imdbmovieapp.network.model.MovieSpecDtoMapper
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
    fun provideSearchMovie(
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

    @ViewModelScoped
    @Provides
    fun provideRestoreMovies(
        movieDao: MovieDao,
        movieEntityMapper: MovieEntityMapper,
    ): RestoreMovies{
        return RestoreMovies(
            movieDao = movieDao,
            entityMapper = movieEntityMapper,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetMovie(
        movieService: MovieService,
        movieSpecDao: MovieSpecDao,
        movieEntityMapper: MovieSpecEntityMapper,
        movieDtoMapper: MovieSpecDtoMapper,
    ): GetMovie{
        return GetMovie(
            movieService = movieService,
            movieSpecDao = movieSpecDao,
            entityMapper = movieEntityMapper,
            dtoMapper = movieDtoMapper,
        )
    }
}