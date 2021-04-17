package com.movie_tmdb.view.pagination

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.movie_tmdb.di.database.NoteRepository
import com.movie_tmdb.model.MovieDetailModel
import com.movie_tmdb.model.MovieListingModel
import com.movie_tmdb.util.AppConstants
import com.movie_tmdb.util.ErrorViewTypes
import com.movie_tmdb.util.NetworkState
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieListDataSource(private val repository: NoteRepository,
private val networkState: MutableLiveData<NetworkState>)
    : PageKeyedDataSource<Int, MovieDetailModel>() {

    private fun getMethod(page: String): Observable<MovieListingModel> {
        return if(AppConstants.SEARCH_KEY.isNullOrEmpty()) repository.getEndPoints().getList(page = page)
        else repository.getEndPoints().getSearchList(page = page, query = AppConstants.SEARCH_KEY)
    }

    @SuppressLint("CheckResult")
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieDetailModel>
    ) {
        networkState.postValue(NetworkState.Loading(status = true))
        getMethod("1").subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onResult(it.results ?: emptyList(), null, 2)
                networkState.postValue(NetworkState.Success(true))
            }, {
                networkState.postValue(NetworkState.Failure(ErrorViewTypes.DIALOG, it))
            }, {
                networkState.postValue(NetworkState.Loading(status = false))
            })
    }

    @SuppressLint("CheckResult")
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieDetailModel>) {
        getMethod(params.key.toString()).subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onResult(it.results ?: emptyList(), params.key+1)
            }, {
                networkState.postValue(NetworkState.Failure(ErrorViewTypes.SNACK_BAR, it))
                }
            )
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MovieDetailModel>
    ) {
    }


}