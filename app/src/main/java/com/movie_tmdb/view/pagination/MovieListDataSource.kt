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
import kotlinx.coroutines.*
import retrofit2.Response

class MovieListDataSource(private val repository: NoteRepository,
                          private val networkState: MutableLiveData<NetworkState>,
                          private val scope: CoroutineScope)
    : PageKeyedDataSource<Int, MovieDetailModel>() {

    private suspend fun getMethod(page: String): Response<MovieListingModel> {
        return if(AppConstants.SEARCH_KEY.isNullOrEmpty()) repository.getEndPoints().getList(page = page)
        else repository.getEndPoints().getSearchList(page = page, query = AppConstants.SEARCH_KEY)
    }

    @SuppressLint("CheckResult")
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieDetailModel>
    ) {
        scope.launch {

            networkState.postValue(NetworkState.Loading(true))
            val response = withContext(Dispatchers.IO){
                getMethod("1")
            }
            if(response.body() != null){
                val list = response.body()?.results ?: emptyList()
                callback.onResult(list, null, if(list.isNotEmpty()) 2 else null)
                networkState.postValue(NetworkState.Success(true))
            } else {
                networkState.postValue(NetworkState.Failure(viewType = ErrorViewTypes.SNACK_BAR,
                    exception = Throwable(response.errorBody().toString())))
            }
            networkState.postValue(NetworkState.Loading(false))
        }

    }

    @SuppressLint("CheckResult")
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieDetailModel>) {
        scope.launch {

            val response = withContext(Dispatchers.IO){
                getMethod(params.key.toString())
            }

            if(response.body() != null){
                val list = response.body()?.results ?: emptyList()
                callback.onResult(list, params.key+1)
                networkState.postValue(NetworkState.Success(true))
            } else {
                networkState.postValue(NetworkState.Failure(viewType = ErrorViewTypes.DIALOG, exception = Throwable(response.errorBody().toString())))
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MovieDetailModel>
    ) {
    }

}