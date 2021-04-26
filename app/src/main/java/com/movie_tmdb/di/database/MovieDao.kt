package com.movie_tmdb.di.database

import androidx.paging.DataSource
import androidx.room.*

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteIntoDb(entity: MovieEntity)

    @Query("select * from movie_db")
    fun getNoteList1(): DataSource.Factory<Int, MovieEntity>

    @Delete
    suspend fun deleteEntity(entity: MovieEntity)//: Completable

    @Query("SELECT * from movie_db where movie_id = :movieId LIMIT 1")
    fun fetchItem(movieId: String): MovieEntity?

}