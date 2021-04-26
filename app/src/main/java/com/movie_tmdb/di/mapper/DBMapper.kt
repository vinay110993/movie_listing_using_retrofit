package com.movie_tmdb.di.mapper

import com.movie_tmdb.di.database.MovieEntity
import com.movie_tmdb.model.MovieDetailModel

interface DBMapper {
    fun convertDbObjectToModel(movieEntity: MovieEntity): MovieDetailModel
    fun convertModelToDbObject(model: MovieDetailModel): MovieEntity
}