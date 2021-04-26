package com.movie_tmdb.view.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.movie_tmdb.di.database.MovieEntity
import com.movie_tmdb.di.database.NoteRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieFavouriteViewModel @Inject constructor(private val repo: NoteRepository): ViewModel(){

    fun getNoteList(): LiveData<PagedList<MovieEntity>> {
        return repo.getPagedList()
    }
}