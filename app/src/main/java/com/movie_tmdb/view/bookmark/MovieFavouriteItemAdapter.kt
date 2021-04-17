package com.movie_tmdb.view.bookmark

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.movie_tmdb.R
import com.movie_tmdb.databinding.ListItemLayoutBinding
import com.movie_tmdb.di.database.MovieEntity
import com.movie_tmdb.util.getDoubleValue
import com.movie_tmdb.util.inflateLayout
import kotlin.math.roundToInt

class MovieFavouriteItemAdapter(private val listener: ViewClick ?= null): PagedListAdapter<MovieEntity, MovieFavouriteItemAdapter.ViewHolder>(DiffCallback) {

    abstract class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        abstract fun bindView(item: MovieEntity?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ItemViewHolder(parent.inflateLayout(R.layout.movie_item_layout))
    }

    inner class ItemViewHolder(private val binding: ListItemLayoutBinding): ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
                listener?.onViewClick(getItem(adapterPosition))
            }
        }

        override fun bindView(item: MovieEntity?)= item?.let {mItem->
            binding.imageUrl = mItem.movieBanner
            binding.repoName = mItem.movieTitle
            binding.rating = ((mItem.rating.getDoubleValue())/2).roundToInt()

            binding.executePendingBindings()
        } ?: kotlin.run {
            binding.unbind()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.movieId == newItem.movieId
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface ViewClick{
        fun onViewClick(item: MovieEntity?)
    }
}