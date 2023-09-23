package com.otaz.montage.di

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
    ): SearchMovies{
        return SearchMovies(
            tmdbApiService = tmdbApiService,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetMostPopularMovies(
        tmdbApiService: TmdbApiService,
    ): GetMostPopularMovies{
        return GetMostPopularMovies(
            tmdbApiService = tmdbApiService,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetUpcomingMovies(
        tmdbApiService: TmdbApiService,
    ): GetUpcomingMovies{
        return GetUpcomingMovies(
            tmdbApiService = tmdbApiService,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetTopRatedMovies(
        tmdbApiService: TmdbApiService,
    ): GetTopRatedMovies{
        return GetTopRatedMovies(
            tmdbApiService = tmdbApiService,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideConfigurations(
        tmdbApiService: TmdbApiService,
    ): GetConfigurations {
        return GetConfigurations(
            tmdbApiService = tmdbApiService,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetTmdbMovie(
        tmdbApiService: TmdbApiService,
    ): GetTmdbMovie {
        return GetTmdbMovie(
            tmdbApiService = tmdbApiService,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetOmdbMovie(
        omdbApiService: OmdbApiService,
    ): GetOmdbMovie {
        return GetOmdbMovie(
            omdbApiService = omdbApiService,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetMovieReviews(
        tmdbApiService: TmdbApiService,
    ): GetMovieReviews{
        return GetMovieReviews(
            tmdbApiService = tmdbApiService,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideSaveMovie(
        movieDao: MovieDao,
    ): SaveMovie{
        return SaveMovie(
            movieDao = movieDao,
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
    ): GetSavedMovies{
        return GetSavedMovies(
            movieDao = movieDao,
        )
    }
}