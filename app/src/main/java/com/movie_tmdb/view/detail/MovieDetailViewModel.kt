package com.movie_tmdb.view.detail

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movie_tmdb.di.database.MovieEntity
import com.movie_tmdb.di.database.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(private val repo: NoteRepository): ViewModel(){

    /**
     * calling repository method to insert ot remove,
     * @param movieEntity: entity need to update
     * @see com.movie_tmdb.di.database.NoteRepository.insertMovieId
     * */
    fun insertsMovieIdExistsInDB(movieEntity: MovieEntity): LiveData<Boolean> {

        val mutableLiveData = MutableLiveData<Boolean>()
        viewModelScope.launch {
            val status = withContext(Dispatchers.IO){
                repo.insertMovieId(entity = movieEntity)
            }
            mutableLiveData.postValue(status)
        }
        return mutableLiveData
    }

    /**
     * calling repository method to check if the id exists in database,
     * @param movieId: id need to check if exists in db
     * @see com.movie_tmdb.di.database.NoteRepository.isMovieExistInDb
     * */
    @SuppressLint("CheckResult")
    fun isMovieIdExistsInDB(movieId: String): LiveData<Boolean> {
        val mutableLiveData = MutableLiveData<Boolean>()
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO){
                repo.isMovieExistInDb(movieId)
            }
            mutableLiveData.postValue(response != null)
        }
        return mutableLiveData
    }

}