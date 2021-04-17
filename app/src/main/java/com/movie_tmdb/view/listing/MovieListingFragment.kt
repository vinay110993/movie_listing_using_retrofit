package com.movie_tmdb.view.listing

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.movie_tmdb.databinding.MovieListingLayoutBinding
import com.movie_tmdb.di.database.MovieEntity
import com.movie_tmdb.model.MovieDetailModel
import com.movie_tmdb.util.*
import com.movie_tmdb.view.BaseFragment
import com.movie_tmdb.view.detail.MovieDetailFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MovieListingFragment: BaseFragment(){

    private var binding: MovieListingLayoutBinding?= null
    @Inject lateinit var factory : ViewModelProvider.Factory
    private var viewModel: MovieListingViewModel ?= null

    private val subject = PublishSubject.create<String>()

    /**
     * adapter to set my items, @param: MovieItemAdapter.ViewClick to get listener of clicks etc...
     * */
    private val adapter: MovieItemAdapter by lazy {
        MovieItemAdapter(object: MovieItemAdapter.ViewClick{
            override fun onViewClick(item: MovieDetailModel?) {
                item ?: return
                val entity = MovieEntity(
                    movieId = item.id?.toString() ?: "",
                    movieTitle =  item.title ?: "",
                    rating = item.voteAverage?.toString() ?: "0",
                    description = item.overview ?: "",
                    movieBanner = item.posterPath ?: "")

                val bundle = Bundle()
                bundle.putParcelable(AppConstants.MOVIEY_ENTTY, entity)

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
        initializeRecyclerView()
        initializeViewModel()
        initializeObserver()
        applySearchViewListener()
        applySearchViewRxObserver()
    }

    /**
     * initialize view model,
     * @see com.movie_tmdb.di.ViewModelModule.bindUserViewModel
     * */
    private fun initializeViewModel(){
        viewModel = factory.create(MovieListingViewModel::class.java)
    }

    /**
     * setting up recycler view by assigning Layout Manager and adapter.
     * */

    private fun initializeRecyclerView(){
        binding?.recyclerView?.layoutManager = LinearLayoutManager(context)
        binding?.recyclerView?.adapter = adapter
    }

    /**
     * initialize observer here and initialize pagination for getting discover movie list
     * datasource:
     * @see com.movie_tmdb.view.pagination.MovieListDataSource
     * dataFactory:
     * @see com.movie_tmdb.view.pagination.MovieListDataFactory
     * */
    private fun initializeObserver(){
        viewModel?.getAccessToken()
        viewModel?.dataList?.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel?.getNetworkStateLiveData()?.observe(viewLifecycleOwner, Observer {state->
            when(state){
                is NetworkState.Failure->{

                    binding?.progressBar?.visibility = View.GONE
                    when(state.viewType){
                        ErrorViewTypes.SNACK_BAR->binding?.recyclerView?.showSnackBar(state.exception.message ?: "some error occur")
                        ErrorViewTypes.DIALOG->context?.showInfoDialog(title = "Alert", message = state.exception.message ?: "")
                        ErrorViewTypes.TOAST->context?.showToastL(state.exception.message ?: "some error occur")
                    }
                }
                is NetworkState.Loading->{
                    binding?.progressBar?.visibility = if(state.status) View.VISIBLE else View.GONE
                }
                is NetworkState.Success->{

                }
            }
        })
    }

    /**
     * apply my search view listeners
     * onchange: extension function
     * and click listener of search icon
     * */

    private fun applySearchViewListener(){
        binding?.editText?.onChange{
            subject.onNext(it)
        }

        binding?.ivSearch?.setOnClickListener {
            subject.onComplete()
        }
    }

    /**
     * using rxjava to apply searching item,
     * operators:
     * distinctUntilChanged: to get distinct values,
     * filters: proceed only, if the searching item has a length multiple of 3,
     * store it on @see AppConstants.SEARCH_KEY
     * and invalidate the list, to restart pagination.
     * */

    @SuppressLint("CheckResult")
    private fun applySearchViewRxObserver(){
        subject.debounce(300, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .filter { t -> t.length % 3 == 0 }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                AppConstants.SEARCH_KEY = it
                adapter.currentList?.dataSource?.invalidate()
            }
    }

    /**
     * provide string to set up screen title
     * */
    override fun provideScreenTitle() = "Listing"
}