package com.movie_tmdb.di

import com.movie_tmdb.model.MovieDetailModel
import com.movie_tmdb.model.MovieListingModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * interface to maintain api endpoints that needed in app.
 * */
interface RetrofitApiEndPoints {
    @GET("discover/movie")
    fun getList(@Query("page") page: String): Observable<MovieListingModel>

    @GET("search/movie")
    fun getSearchList(@Query("page") page: String,
                @Query("query") query: String?): Observable<MovieListingModel>

    @GET("movie/{name}")
    fun getDetails(@Path(value = "name", encoded = false) name: String): Observable<MovieDetailModel>
}