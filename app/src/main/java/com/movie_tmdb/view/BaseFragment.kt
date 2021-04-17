package com.movie_tmdb.view

import android.content.Context
import androidx.fragment.app.Fragment
import com.movie_tmdb.R
import com.movie_tmdb.view.bookmark.MovieFavouriteListFragment
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment

open class BaseFragment: DaggerFragment() {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    fun addFragment(fragment: Fragment){
        val ft = activity?.supportFragmentManager?.beginTransaction()
        ft?.add(R.id.container, fragment)?.addToBackStack(fragment.tag)?.commit()
    }

    open fun provideScreenTitle(): String= ""
    open fun showBackButton(): Boolean = true
    open fun showBookMarkButton(): Boolean = true
    fun onBookMarkIconClick() {
         addFragment(MovieFavouriteListFragment())
     }

}