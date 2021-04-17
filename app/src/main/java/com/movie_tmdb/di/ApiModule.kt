package com.movie_tmdb.di

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.movie_tmdb.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


/**
 * injections related to api actions, specifically Retrofit
 * */
@Module
class ApiModule {

    @Provides
    fun provideGsonInstance(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    fun provideCache(application: Application): Cache {
        val cacheSize = 5 * 1024 * 1024.toLong() // 5 MB
        val httpCacheDirectory = File(application.cacheDir, "http-cache")
        return Cache(httpCacheDirectory, cacheSize)
    }

    private val httpInterceptor= Interceptor { chain->
        val originalRequest = chain.request()
        val url = originalRequest.url()

        val updateUrl = url.newBuilder()
            .addQueryParameter("api_key", BuildConfig.MOVIE_DB_API_KEY)
            .build()

        val requestBuilder = originalRequest.newBuilder().url(updateUrl)
        chain.proceed(requestBuilder.build())
    }

    @Provides
    fun provideOkHttpClientInstance(cache: Cache): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC

        val httpClient = OkHttpClient.Builder()
        httpClient.cache(cache)
        httpClient.addNetworkInterceptor(httpInterceptor)
        httpClient.addInterceptor(logging)
        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        return httpClient.build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient, gson: Gson):Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    fun provideRetrofitEndPoint(retroFit: Retrofit): RetrofitApiEndPoints {
        return retroFit.create(RetrofitApiEndPoints::class.java)
    }
}