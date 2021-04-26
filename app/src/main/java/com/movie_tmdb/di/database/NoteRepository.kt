package com.movie_tmdb.di.database

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.movie_tmdb.di.RetrofitApiEndPoints
import com.movie_tmdb.util.AppConstants
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
     * method to insert an entity into Database
     * @param: entity: MovieEntity
     * */
    private suspend fun insertIntoDb(entity: MovieEntity) {
        return dao.insertNoteIntoDb(entity)
    }

    /**
     * method to deleting an entity into Database
     * @param: entity: MovieEntity
     * */
    private suspend fun deleteFromDb(entity: MovieEntity){
        return dao.deleteEntity(entity)
    }

    /**
     * performs action, by checking if entity exists in db or not
     *
     * firstly it checks if movieId. of entity exists in database,
     * @see isMovieExistInDb
     * if yes: it insert into database, by creating a separate observer after completing the action
     * @see insertIntoDb
     * if no: it delete from database, by creating a separate observer after completing the action
     * @see deleteFromDb
     * */

    @SuppressLint("CheckResult")
    suspend fun insertMovieId(entity: MovieEntity): Boolean{
        val movieEntity = isMovieExistInDb(entity.movieId)
        return if(movieEntity == null){
            dao.insertNoteIntoDb(entity)
            true
        } else {
            dao.deleteEntity(movieEntity)
            false
        }
    }

    fun isMovieExistInDb(movieId: String) = dao.fetchItem(movieId)

}