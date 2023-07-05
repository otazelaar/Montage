package com.otaz.montage.di

import com.otaz.montage.cache.model.MovieEntityMapper
import com.otaz.montage.interactors.app.DeleteMovie
import com.otaz.montage.interactors.app.GetConfigurations
import com.otaz.montage.interactors.app.GetSavedMovies
import com.otaz.montage.interactors.app.SaveMovie
import com.otaz.montage.interactors.movie_detail.GetMovieReviews
import com.otaz.montage.interactors.movie_detail.GetOmdbMovie
import com.otaz.montage.interactors.movie_detail.GetTmdbMovie
import com.otaz.montage.interactors.movie_list.GetMostPopularMovies
import com.otaz.montage.interactors.movie_list.GetTopRatedMovies
import com.otaz.montage.interactors.movie_list.GetUpcomingMovies
import com.otaz.montage.interactors.movie_list.SearchMovies
import com.otaz.montage.network.OmdbApiService
import com.otaz.montage.network.TmdbApiService
import com.otaz.montage.network.model.*
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

    @ViewModelScoped
    @Provides
    fun provideSaveMovie(
        movieDao: MovieDao,
        movieEntityMapper: MovieEntityMapper,
    ): SaveMovie{
        return SaveMovie(
            movieDao = movieDao,
            movieEntityMapper = movieEntityMapper,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideDeleteMovie(
        movieDao: MovieDao,
    ): DeleteMovie{
        return DeleteMovie(
            movieDao = movieDao,
        )
    }

    @ViewModelScoped
    @Provides
    fun getSavedMovies(
        movieDao: MovieDao,
        movieEntityMapper: MovieEntityMapper,
    ): GetSavedMovies{
        return GetSavedMovies(
            movieDao = movieDao,
            movieEntityMapper = movieEntityMapper,
        )
    }
}