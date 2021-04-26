package com.movie_tmdb.di.mapper

import com.movie_tmdb.di.database.MovieEntity
import com.movie_tmdb.model.MovieDetailModel
import javax.inject.Inject

class DBMapperImpl @Inject constructor() : DBMapper {
    override fun convertDbObjectToModel(movieEntity: MovieEntity): MovieDetailModel {
        TODO("Not yet implemented")
    }

    override fun convertModelToDbObject(model: MovieDetailModel): MovieEntity {
        TODO("Not yet implemented")
    }
}