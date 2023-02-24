package com.otaz.imdbmovieapp.di

import com.otaz.imdbmovieapp.interactors.app.GetConfigurations
import com.otaz.imdbmovieapp.interactors.movie_detail.GetMovieReviews
import com.otaz.imdbmovieapp.interactors.movie_detail.GetOmdbMovie
import com.otaz.imdbmovieapp.interactors.movie_detail.GetTmdbMovie
import com.otaz.imdbmovieapp.interactors.movie_list.GetMostPopularMovies
import com.otaz.imdbmovieapp.interactors.movie_list.GetTopRatedMovies
import com.otaz.imdbmovieapp.interactors.movie_list.GetUpcomingMovies
import com.otaz.imdbmovieapp.interactors.movie_list.SearchMovies
import com.otaz.imdbmovieapp.network.OmdbApiService
import com.otaz.imdbmovieapp.network.TmdbApiService
import com.otaz.imdbmovieapp.network.model.*
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
        tmdbApiService: TmdbApiService,
        movieDtoMapper: MovieDtoMapper,
    ): SearchMovies{
        return SearchMovies(
            tmdbApiService = tmdbApiService,
            dtoMapper = movieDtoMapper,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetMostPopularMovies(
        tmdbApiService: TmdbApiService,
        movieDtoMapper: MovieDtoMapper,
    ): GetMostPopularMovies{
        return GetMostPopularMovies(
            tmdbApiService = tmdbApiService,
            dtoMapper = movieDtoMapper,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetUpcomingMovies(
        tmdbApiService: TmdbApiService,
        movieDtoMapper: MovieDtoMapper,
    ): GetUpcomingMovies{
        return GetUpcomingMovies(
            tmdbApiService = tmdbApiService,
            dtoMapper = movieDtoMapper,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetTopRatedMovies(
        tmdbApiService: TmdbApiService,
        movieDtoMapper: MovieDtoMapper,
    ): GetTopRatedMovies{
        return GetTopRatedMovies(
            tmdbApiService = tmdbApiService,
            dtoMapper = movieDtoMapper,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideConfigurations(
        tmdbApiService: TmdbApiService,
        configurationsDtoMapper: ConfigsDtoMapper,
    ): GetConfigurations {
        return GetConfigurations(
            tmdbApiService = tmdbApiService,
            configurationsDtoMapper = configurationsDtoMapper,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetTmdbMovie(
        tmdbApiService: TmdbApiService,
        tmdbMovieSpecsDtoMapper: TmdbMovieSpecsDtoMapper,
    ): GetTmdbMovie {
        return GetTmdbMovie(
            tmdbApiService = tmdbApiService,
            tmdbMovieSpecDtoMapper = tmdbMovieSpecsDtoMapper,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetOmdbMovie(
        omdbApiService: OmdbApiService,
        omdbMoviesSpecsDtoMapper: OmdbMoviesSpecsDtoMapper,
    ): GetOmdbMovie {
        return GetOmdbMovie(
            omdbApiService = omdbApiService,
            omdbMovieSpecDtoMapper = omdbMoviesSpecsDtoMapper,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetMovieReviews(
        tmdbApiService: TmdbApiService,
        movieReviewsDtoMapper: MovieReviewsDtoMapper,
    ): GetMovieReviews{
        return GetMovieReviews(
            tmdbApiService = tmdbApiService,
            movieReviewsDtoMapper = movieReviewsDtoMapper,
        )
    }
}