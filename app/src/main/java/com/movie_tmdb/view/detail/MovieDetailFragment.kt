package com.movie_tmdb.view.detail

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.movie_tmdb.R
import com.movie_tmdb.databinding.MovieDetailLayoutBinding
import com.movie_tmdb.di.database.MovieEntity
import com.movie_tmdb.util.AppConstants
import com.movie_tmdb.util.getDoubleValue
import com.movie_tmdb.util.getIntValue
import com.movie_tmdb.util.inflateLayout
import com.movie_tmdb.view.BaseFragment
import javax.inject.Inject
import kotlin.math.roundToInt

class MovieDetailFragment: BaseFragment(){

    private var binding: MovieDetailLayoutBinding?= null
    @Inject
    lateinit var factory : ViewModelProvider.Factory
    private var viewModel: MovieDetailViewModel ?= null

    /**
     * requested movieEntity.
     * */
    private val movieEntity: MovieEntity? by lazy {
        arguments?.getParcelable(AppConstants.MOVIEY_ENTTY) as? MovieEntity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = container?.inflateLayout<MovieDetailLayoutBinding>(R.layout.movie_detail_layout)
        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewModel()
        setMovieData()
        isMovieIdExistInDb()
        clickListener()
    }

    /**
     * initialize view model,
     * @see com.movie_tmdb.di.ViewModelModule
     * */
    private fun initializeViewModel(){
        viewModel = factory.create(MovieDetailViewModel::class.java)
    }

    /**
     * method to set up views values, we could share the entity object to the data binding too,
     * in that case, we need to take extra care of null values, it must be handled while using data class object.
     * */
    private fun setMovieData(){
        binding?.imageUrl = movieEntity?.movieBanner ?: ""
        binding?.title = movieEntity?.movieTitle ?: ""
        binding?.rating = (movieEntity?.rating.getDoubleValue()/2).roundToInt()
        binding?.description = movieEntity?.description ?: ""

        binding?.executePendingBindings()
    }

    /**
     * set click listener here, mainly clicking on favourite icon,
     * cases: remove favourite if it the {@see MovieEntity} already exists in database,
     * else add it into database,
     * and manage the favourite color correspondingly
     * */
    private fun clickListener(){
        binding?.rlFavourite?.setOnClickListener{
            movieEntity ?: return@setOnClickListener
            viewModel?.insertsMovieIdExistsInDB(movieEntity!!)?.observe(viewLifecycleOwner, Observer {
                binding?.ivFavourite?.backgroundTintList = ColorStateList.valueOf(if(it) Color.RED else Color.GRAY)
            })
        }
    }


    /**
     * method check if the particular requested movieEntity exists in database already,
     * and manage the favourite icon color correspondingly.
     * */
    private fun isMovieIdExistInDb(){
        viewModel?.isMovieIdExistsInDB(movieEntity?.movieId ?: "")?.observe(viewLifecycleOwner, Observer {
            binding?.ivFavourite?.backgroundTintList = ColorStateList.valueOf(if(it) Color.RED else Color.GRAY)
        })
    }

//    private fun applyObserver(){
//        viewModel?.getMovieDetail(movieId)?.observe(viewLifecycleOwner, Observer {
//            binding?.imageUrl = it.posterPath
//            binding?.title = it.title
//            binding?.executePendingBindings()
//        })
//    }

    /**
     * provide string to set up screen title
     * */
    override fun provideScreenTitle() = movieEntity?.movieTitle ?: "Movie"

}