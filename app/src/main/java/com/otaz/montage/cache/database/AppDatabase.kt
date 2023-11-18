package com.otaz.montage.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.otaz.montage.cache.model.MovieEntity
import com.otaz.montage.cache.MovieDao

@Database(entities = [MovieEntity::class], version = 10)
abstract class AppDatabase: RoomDatabase(){

    abstract fun movieDao(): MovieDao

    companion object{
        val DATABASE_NAME = "movie_db_v9"
    }
}