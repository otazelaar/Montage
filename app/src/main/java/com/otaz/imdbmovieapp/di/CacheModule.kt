package com.otaz.imdbmovieapp.di

import androidx.room.Room
import com.otaz.imdbmovieapp.cache.database.AppDatabase
import com.otaz.imdbmovieapp.cache.model.MovieEntityMapper
import com.otaz.imdbmovieapp.network.model.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideDb(app: BaseApplication): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(app: AppDatabase): MovieDao {
        return app.movieDao()
    }

    @Singleton
    @Provides
    fun provideCacheMovieMapper(): MovieEntityMapper {
        return MovieEntityMapper()
    }
}