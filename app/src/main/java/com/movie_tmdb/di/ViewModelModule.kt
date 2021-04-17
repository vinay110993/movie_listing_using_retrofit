package com.movie_tmdb.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.movie_tmdb.view.bookmark.MovieFavouriteViewModel
import com.movie_tmdb.view.detail.MovieDetailViewModel
import com.movie_tmdb.view.listing.MovieListingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * inkections belongs to viewModel
 * */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieListingViewModel::class)
    abstract fun bindUserViewModel(viewModel: MovieListingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    abstract fun bindDetailViewModel(viewModel: MovieDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieFavouriteViewModel::class)
    abstract fun bindFavouriteViewModel(viewModel: MovieFavouriteViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelCustomFactory): ViewModelProvider.Factory
}