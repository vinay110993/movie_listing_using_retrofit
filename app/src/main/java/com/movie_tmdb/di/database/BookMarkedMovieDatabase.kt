package com.movie_tmdb.di.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 2, exportSchema = false)
abstract class BookMarkedMovieDatabase: RoomDatabase(){
    abstract fun provideNoteDao(): MovieDao
}