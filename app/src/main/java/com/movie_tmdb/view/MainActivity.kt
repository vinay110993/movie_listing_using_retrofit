package com.movie_tmdb.view

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.movie_tmdb.R
import com.movie_tmdb.databinding.ActivityMainBinding
import com.movie_tmdb.view.listing.MovieListingFragment
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    private fun getCurrentFragment()= supportFragmentManager.findFragmentById(R.id.container) as? BaseFragment
    var binding: ActivityMainBinding ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )

        supportFragmentManager.addOnBackStackChangedListener {
            manageToolBar(getCurrentFragment())
        }
        addFragment(MovieListingFragment())

        binding?.ivBookmark?.setOnClickListener {
            getCurrentFragment()?.onBookMarkIconClick()
        }

        binding?.ivBack?.setOnClickListener {
            onBackPressed()
        }
    }

    private fun manageToolBar(fragment: BaseFragment?) = fragment?.let{mFragment->
        binding?.tvTitle?.text = mFragment.provideScreenTitle()
        binding?.ivBack?.visibility = if(mFragment.showBackButton()) View.VISIBLE else View.GONE
        binding?.ivBookmark?.visibility = if(mFragment.showBookMarkButton()) View.VISIBLE else View.GONE
    }

    private fun addFragment(fragment: BaseFragment){
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.container, fragment).addToBackStack(fragment.tag).commit()
    }

    override fun onBackPressed() {
        val fragmentCount = supportFragmentManager.backStackEntryCount

        if(fragmentCount > 1) super.onBackPressed()
        else finish()
    }

}

