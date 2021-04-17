package com.movie_tmdb.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.movie_tmdb.di.database.BookMarkedMovieDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * injections related to local db actions, specifically Database
 * */
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideRoomDatabaseInstance(context: Context): BookMarkedMovieDatabase {
        return Room.databaseBuilder(context, BookMarkedMovieDatabase::class.java, "note_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteDao(db: BookMarkedMovieDatabase) = db.provideNoteDao()

}