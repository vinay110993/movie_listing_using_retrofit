package com.movie_tmdb.di.database

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.movie_tmdb.di.RetrofitApiEndPoints
import com.movie_tmdb.util.AppConstants
import io.reactivex.*
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NoteRepository @Inject constructor(private val dao: MovieDao,
                                         private val endPoints: RetrofitApiEndPoints) {

    private var config = PagedList.Config.Builder()
        .setPageSize(AppConstants.PAGE_SIZE)
        .setInitialLoadSizeHint(AppConstants.PAGE_INITIAL_LOAD_SIZE_HINT)
        .setPrefetchDistance(AppConstants.PAGE_PREFETCH_DISTANCE)
        .setEnablePlaceholders(true)
        .build()

    /**
     * provide pagination, datasource: Room Database
     * */
    fun getPagedList(): LiveData<PagedList<MovieEntity>> {
        val factory: DataSource.Factory<Int, MovieEntity> = dao.getNoteList1()
        return LivePagedListBuilder(factory, config)
            .build()
    }

    /**
     * provide end point to perform api actions
     * */
    fun getEndPoints() = endPoints

    /**
     * method to check if a specific id exists in databse or not, by providing count.
     * @param: movieId
     * */
    fun userCount(movieId: String): Observable<Int>{
        return dao.usersCount(movieId).toObservable()
    }

    /**
     * method to insert an entity into Database
     * @param: entity: MovieEntity
     * */
    private fun insertIntoDb(entity: MovieEntity) {
        return dao.insertNoteIntoDb(entity)
    }

    /**
     * method to deleting an entity into Database
     * @param: entity: MovieEntity
     * */
    private fun deleteFromDb(entity: MovieEntity){
        return dao.deleteEntity(entity)
    }

    /**
     * performs action, by checking if entity exists in db or not
     *
     * firstly it checks if movieId. of entity exists in database,
     * @see userCount
     * if yes: it insert into database, by creating a separate observer after completing the action
     * @see insertIntoDb
     * if no: it delete from database, by creating a separate observer after completing the action
     * @see deleteFromDb
     * */

    @SuppressLint("CheckResult")
    fun insertMovieId(entity: MovieEntity): Observable<Boolean>{
        return userCount(entity.movieId)
            .subscribeOn(Schedulers.io())
            .flatMap {
                if(it <= 0) Completable.fromAction { insertIntoDb(entity) }.andThen(Observable.just(true))
                else Completable.fromAction { deleteFromDb(entity) }.andThen(Observable.just(false))
            }
    }

}