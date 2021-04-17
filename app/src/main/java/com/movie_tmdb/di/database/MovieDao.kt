package com.movie_tmdb.di.database

import androidx.annotation.WorkerThread
import androidx.paging.DataSource
import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface MovieDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @WorkerThread
    fun insertNoteIntoDb(entity: MovieEntity)//: Completable

    @Query("select * from movie_db")
    @WorkerThread
    fun getNoteList1(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT COUNT(*) from movie_db where movie_id = :movieId")
    fun usersCount(movieId: String) : Single<Int>

    @Delete
    fun deleteEntity(entity: MovieEntity)//: Completable
}