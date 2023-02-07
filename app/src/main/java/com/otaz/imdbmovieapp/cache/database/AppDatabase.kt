package com.otaz.imdbmovieapp.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.otaz.imdbmovieapp.cache.model.MovieEntity
import com.otaz.imdbmovieapp.cache.model.MovieSpecsEntity
import com.otaz.imdbmovieapp.network.model.MovieDao
import com.otaz.imdbmovieapp.network.model.MovieSpecDao

@Database(entities = [MovieEntity::class, MovieSpecsEntity::class], version = 8)
abstract class AppDatabase: RoomDatabase(){

    abstract fun movieDao(): MovieDao
    abstract fun movieSpecDao(): MovieSpecDao

    companion object{
        val DATABASE_NAME = "movie_db"
    }
}