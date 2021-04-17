package com.movie_tmdb.view.detail

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.movie_tmdb.di.database.MovieEntity
import com.movie_tmdb.di.database.NoteRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(private val repo: NoteRepository): ViewModel(){

    private var disposable: CompositeDisposable ?= null
    init {
        disposable = CompositeDisposable()
    }

    /**
     * calling api to get movie details with specific movieId,
     * but now using parcelable in bundle to pass from previous screen
     * */

//    @SuppressLint("CheckResult")
//    fun getMovieDetail(movieId: String): LiveData<MovieDetailModel>{
//        val mutableLiveData = MutableLiveData<MovieDetailModel>()
//
//        disposable?.add(
//            repo.getEndPoints().getDetails(movieId)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    mutableLiveData.postValue(it)
//                }, {
//                    it.printStackTrace()
//                })
//        )
//        return mutableLiveData
//    }

    /**
     * calling repository method to insert ot remove,
     * @param movieEntity: entity need to update
     * @see com.movie_tmdb.di.database.NoteRepository.insertMovieId
     * */
    @SuppressLint("CheckResult")
    fun insertsMovieIdExistsInDB(movieEntity: MovieEntity): LiveData<Boolean>{
        val mutableLiveData = MutableLiveData<Boolean>()

        disposable?.add(
            repo.insertMovieId(movieEntity )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mutableLiveData.postValue(it)
                }
        )
        return mutableLiveData
    }

    /**
     * calling repository method to check if the id exists in database,
     * @param movieId: id need to check if exists in db
     * @see com.movie_tmdb.di.database.NoteRepository.userCount
     * */
    @SuppressLint("CheckResult")
    fun isMovieIdExistsInDB(movieId: String): LiveData<Boolean>{
        val mutableLiveData = MutableLiveData<Boolean>()
        disposable?.add(
            repo.userCount(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mutableLiveData.postValue(it > 0)
                }
        )
        return mutableLiveData
    }

    /**
     * disposing my observables, when view model cleared
     * */
    override fun onCleared() {
        super.onCleared()
        if(disposable != null && disposable?.isDisposed == false) disposable?.dispose()
    }

}