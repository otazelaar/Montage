package com.otaz.montage.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.otaz.montage.cache.model.MovieEntity
import com.otaz.montage.network.model.MovieDao

@Database(entities = [MovieEntity::class], version = 2)
abstract class AppDatabase: RoomDatabase(){

    abstract fun movieDao(): MovieDao

    companion object{
        val DATABASE_NAME = "movie_db_IMDB"
    }
}