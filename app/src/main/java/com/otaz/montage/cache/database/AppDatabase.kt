package com.otaz.montage.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.otaz.montage.cache.model.MovieEntity
import com.otaz.montage.cache.MovieDao
import com.otaz.montage.cache.model.MovieWatchListEntity

@Database(entities = [MovieEntity::class, MovieWatchListEntity::class], version = 3)
abstract class AppDatabase: RoomDatabase(){

    abstract fun movieDao(): MovieDao

    companion object{
        val DATABASE_NAME = "movie_db_v3"
    }
}