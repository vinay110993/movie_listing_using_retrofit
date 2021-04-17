package com.movie_tmdb.view.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.movie_tmdb.databinding.MovieListingLayoutBinding
import com.movie_tmdb.di.database.MovieEntity
import com.movie_tmdb.util.AppConstants
import com.movie_tmdb.view.BaseFragment
import com.movie_tmdb.view.detail.MovieDetailFragment
import javax.inject.Inject

class MovieFavouriteListFragment: BaseFragment(){

    private var binding: MovieListingLayoutBinding?= null
    @Inject lateinit var factory : ViewModelProvider.Factory
    private var viewModel: MovieFavouriteViewModel ?= null

    /**
     * adapter to set my items, @param: MovieFavouriteItemAdapter.ViewClick to get listener of clicks etc...
     * */
    private val adapter: MovieFavouriteItemAdapter by lazy {
        MovieFavouriteItemAdapter(object: MovieFavouriteItemAdapter.ViewClick{
            override fun onViewClick(item: MovieEntity?) {
                val bundle = Bundle()
                bundle.putParcelable(AppConstants.MOVIEY_ENTTY, item)

                addFragment(MovieDetailFragment().also { it.arguments = bundle })
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MovieListingLayoutBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewModel()
        setUpViews()
        initializeObserver()
    }

    /**
     * initialize view model,
     * @see com.movie_tmdb.di.ViewModelModule.bindFavouriteViewModel
     * */
    private fun initializeViewModel(){
        viewModel = factory.create(MovieFavouriteViewModel::class.java)
    }

    /**
     * setting up recycler view by assigning Layout Manager and adapter
     * and other views by hiding search view and progress bar
     * */
    private fun setUpViews(){
        binding?.searchView?.visibility = View.GONE
        binding?.progressBar?.visibility = View.GONE

        binding?.recyclerView?.layoutManager = LinearLayoutManager(context)
        binding?.recyclerView?.adapter = adapter
    }
    /**
     * initialize pagination, datasource: database.
     * */
    private fun initializeObserver(){
        viewModel?.getNoteList()?.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    /**
     * provide string to set up screen title
     * */
    override fun provideScreenTitle() = "Favourites"

    /**
     * possible value: true/false
     * no need to show favourite icon on the screen, so pass false here
     * */
    override fun showBookMarkButton() = false

}