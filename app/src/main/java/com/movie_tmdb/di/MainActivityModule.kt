package com.movie_tmdb.di

import com.movie_tmdb.view.bookmark.MovieFavouriteListFragment
import com.movie_tmdb.view.detail.MovieDetailFragment
import com.movie_tmdb.view.listing.MovieListingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * injections need to complete functionality belongs to MainActivity
 * */
@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector
    abstract fun bindListFragment(): MovieListingFragment

    @ContributesAndroidInjector
    abstract fun bindDetailFragment(): MovieDetailFragment

    @ContributesAndroidInjector
    abstract fun bindFavouriteFragment(): MovieFavouriteListFragment
}