package com.movie_tmdb.view.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.movie_tmdb.di.database.NoteRepository
import com.movie_tmdb.model.MovieDetailModel
import com.movie_tmdb.util.NetworkState
import kotlinx.coroutines.CoroutineScope

class MovieListDataFactory(val repository: NoteRepository,
                           val networkState: MutableLiveData<NetworkState>,
                           private val scope: CoroutineScope
): DataSource.Factory<Int, MovieDetailModel>() {

    private val liveDataSource = MutableLiveData<PageKeyedDataSource<Int, MovieDetailModel>>()
    override fun create(): DataSource<Int, MovieDetailModel> {
        val dataSource = MovieListDataSource(repository = repository, networkState = networkState, scope = scope)
        liveDataSource.postValue(dataSource)
        return dataSource
    }

}