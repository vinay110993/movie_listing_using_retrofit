package com.movie_tmdb.view.listing

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.movie_tmdb.R
import com.movie_tmdb.databinding.ListItemLayoutBinding
import com.movie_tmdb.model.MovieDetailModel
import com.movie_tmdb.util.inflateLayout
import kotlin.math.roundToInt

class MovieItemAdapter(private val listener: ViewClick ?= null): PagedListAdapter<MovieDetailModel, MovieItemAdapter.ViewHolder>(DiffCallback) {

    abstract class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        abstract fun bindView(item: MovieDetailModel?)
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

        override fun bindView(item: MovieDetailModel?)= item?.let {mItem->
            binding.imageUrl = mItem.posterPath
            binding.repoName = mItem.title
            binding.rating = ((mItem.voteAverage ?: 0.0)/2).roundToInt()

            binding.executePendingBindings()
        } ?: kotlin.run {
            binding.unbind()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<MovieDetailModel>() {
            override fun areItemsTheSame(oldItem: MovieDetailModel, newItem: MovieDetailModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieDetailModel, newItem: MovieDetailModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface ViewClick{
        fun onViewClick(item: MovieDetailModel?)
    }
}