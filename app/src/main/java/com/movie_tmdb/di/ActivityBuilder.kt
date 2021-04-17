package com.movie_tmdb.di;

import com.movie_tmdb.view.MainActivity
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * maintains my injection in views
 * */
@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity
}

