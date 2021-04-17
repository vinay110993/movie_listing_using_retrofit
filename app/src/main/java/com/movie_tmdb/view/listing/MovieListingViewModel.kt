package com.movie_tmdb.view.listing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.movie_tmdb.di.database.NoteRepository
import com.movie_tmdb.model.MovieDetailModel
import com.movie_tmdb.util.NetworkState
import com.movie_tmdb.view.pagination.MovieListDataFactory
import javax.inject.Inject

class MovieListingViewModel @Inject constructor(private val repo: NoteRepository): ViewModel(){

    var dataList: LiveData<PagedList<MovieDetailModel>>?= null

    private val _networkStateLiveData= MutableLiveData<NetworkState>()

    fun getNetworkStateLiveData(): LiveData<NetworkState> = _networkStateLiveData

    fun getAccessToken() {
        val dataFactory = MovieListDataFactory(repository = repo, networkState = _networkStateLiveData)
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPageSize(10)
            .build()
        dataList = LivePagedListBuilder<Int, MovieDetailModel>(dataFactory, config).build()
    }
}