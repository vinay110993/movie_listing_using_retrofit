package com.movie_tmdb.di.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movie_db")
data class MovieEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "movie_id")
    val movieId: String = "",

    @ColumnInfo(name = "title")
    val movieTitle: String="",
    @ColumnInfo(name = "banner")
    val movieBanner: String="",

    @ColumnInfo(name = "rating")
    var rating: String="",

    @ColumnInfo(name = "description")
    val description: String = ""

): Parcelable