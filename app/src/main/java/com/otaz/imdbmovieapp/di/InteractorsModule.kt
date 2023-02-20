package com.otaz.imdbmovieapp.di

import com.otaz.imdbmovieapp.interactors.app.GetConfigurations
import com.otaz.imdbmovieapp.interactors.movie.GetMovie
import com.otaz.imdbmovieapp.interactors.movie_list.GetMostPopularMovies
import com.otaz.imdbmovieapp.interactors.movie_list.GetUpcomingMovies
import com.otaz.imdbmovieapp.interactors.movie_list.SearchMovies
import com.otaz.imdbmovieapp.network.MovieService
import com.otaz.imdbmovieapp.network.model.ConfigsDtoMapper
import com.otaz.imdbmovieapp.network.model.MovieDtoMapper
import com.otaz.imdbmovieapp.network.model.MovieSpecsDtoMapper
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
        movieDtoMapper: MovieDtoMapper,
    ): SearchMovies{
        return SearchMovies(
            movieService = movieService,
            dtoMapper = movieDtoMapper,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetMostPopularMovies(
        movieService: MovieService,
        movieDtoMapper: MovieDtoMapper,
    ): GetMostPopularMovies{
        return GetMostPopularMovies(
            movieService = movieService,
            dtoMapper = movieDtoMapper,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetUpcomingMovies(
        movieService: MovieService,
        movieDtoMapper: MovieDtoMapper,
    ): GetUpcomingMovies{
        return GetUpcomingMovies(
            movieService = movieService,
            dtoMapper = movieDtoMapper,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideConfigurations(
        movieService: MovieService,
        configurationsDtoMapper: ConfigsDtoMapper,
    ): GetConfigurations {
        return GetConfigurations(
            movieService = movieService,
            configurationsDtoMapper = configurationsDtoMapper,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetMovie(
        movieService: MovieService,
        movieSpecDtoMapper: MovieSpecsDtoMapper,
    ): GetMovie {
        return GetMovie(
            movieService = movieService,
            movieSpecDtoMapper = movieSpecDtoMapper,
        )
    }
}