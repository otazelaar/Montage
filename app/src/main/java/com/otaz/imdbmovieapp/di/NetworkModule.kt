package com.otaz.imdbmovieapp.di

import com.otaz.imdbmovieapp.network.MovieService
import com.otaz.imdbmovieapp.network.model.MovieDtoMapper
import com.otaz.imdbmovieapp.network.model.MovieSpecDtoMapper
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
    fun provideMovieSpecMapper(): MovieSpecDtoMapper {
        return MovieSpecDtoMapper()
    }

    @Singleton
    @Provides
    fun provideMovieService(): MovieService {
        val client = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS).build();
        return Retrofit.Builder().baseUrl("https://www.omdbapi.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieService::class.java)
    }

    @Singleton
    @Provides
    @Named("apikey")
    fun provideApiKey(): String{
        return "f59dfd2c"
    }

}