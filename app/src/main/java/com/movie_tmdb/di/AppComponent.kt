package com.movie_tmdb.di

import android.app.Application
import com.movie_tmdb.MyApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Component(modules = [ApiModule::class, AndroidInjectionModule::class, ActivityBuilder::class, ViewModelModule::class, DatabaseModule::class])
@Singleton
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(application: MyApplication)
}