package com.otaz.imdbmovieapp.di

import com.otaz.imdbmovieapp.network.OmdbApiService
import com.otaz.imdbmovieapp.network.TmdbApiService
import com.otaz.imdbmovieapp.network.model.ConfigsDtoMapper
import com.otaz.imdbmovieapp.network.model.MovieDtoMapper
import com.otaz.imdbmovieapp.network.model.OmdbMoviesSpecsDtoMapper
import com.otaz.imdbmovieapp.network.model.TmdbMovieSpecsDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideMovieMapper(): MovieDtoMapper {
        return MovieDtoMapper()
    }

    @Singleton
    @Provides
    fun provideConfigurationMapper(): ConfigsDtoMapper {
        return ConfigsDtoMapper()
    }

    @Singleton
    @Provides
    fun provideTmdbMovieSpecsMapper(): TmdbMovieSpecsDtoMapper {
        return TmdbMovieSpecsDtoMapper()
    }

    @Singleton
    @Provides
    fun provideOmdbMovieSpecsMapper(): OmdbMoviesSpecsDtoMapper {
        return OmdbMoviesSpecsDtoMapper()
    }

    @Singleton
    @Provides
    fun provideTmdbApiService(): TmdbApiService {
        val client = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS).build();
        return Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TmdbApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideOmdbApiService(): OmdbApiService {
        val client = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS).build();
        return Retrofit.Builder().baseUrl("https://www.omdbapi.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OmdbApiService::class.java)
    }

    @Singleton
    @Provides
    @Named("omdb_apikey")
    fun provideOmdbApiKey(): String{
        return "f59dfd2c"
    }

    @Singleton
    @Provides
    @Named("tmdb_apikey")
    fun provideTmdbApiKey(): String{
        return "987919538d8b8520ab30e57e981971bf"
    }

}