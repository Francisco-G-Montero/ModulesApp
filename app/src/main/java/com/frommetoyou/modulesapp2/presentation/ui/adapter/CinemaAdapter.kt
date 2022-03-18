package com.frommetoyou.modulesapp2.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.frommetoyou.modulesapp2.R
import com.frommetoyou.modulesapp2.data.model.*

class CinemaAdapter :
    ListAdapter<CinemaModel, CinemaAdapter.CinemaViewHolder>(CinemaDiffCallback()), CinemaClickListeners {

    class CinemaViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun onBind(cinemaModel: CinemaModel, cinemaClickListeners: CinemaClickListeners) {
            when (cinemaModel) {
                is CinemaModel.TerrorMovie -> bindTerrorMovie(cinemaModel)
                is CinemaModel.DramaMovie -> bindDramaMovie(cinemaModel)
                is CinemaModel.Header -> bindMovieHeader(cinemaModel, cinemaClickListeners)
            }
        }

        private fun bindMovieHeader(cinemaModel: CinemaModel.Header, cinemaClickListeners: CinemaClickListeners) {
            itemView.findViewById<TextView>(R.id.tv_header).text = cinemaModel.title
            itemView.findViewById<ImageButton>(R.id.btn_hide_movies).setOnClickListener {
                cinemaClickListeners.hideDramas()
            }
        }

        private fun bindDramaMovie(cinemaModel: CinemaModel.DramaMovie) {
            itemView.findViewById<TextView>(R.id.tv_title).text = cinemaModel.title
            itemView.findViewById<ImageView>(R.id.img_portrait).setColorFilter(cinemaModel.color)
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "DRAMA", Toast.LENGTH_SHORT).show()
            }
            if (cinemaModel.showElement) itemView.visibility =
                View.VISIBLE else itemView.visibility = View.GONE
        }

        private fun bindTerrorMovie(cinemaModel: CinemaModel.TerrorMovie) {
            itemView.findViewById<TextView>(R.id.tv_title).text = cinemaModel.title
            itemView.findViewById<ImageView>(R.id.img_portrait).setColorFilter(cinemaModel.color)
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "TERROR", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CinemaViewHolder {
        val layout = when (viewType) {
            TYPE_MOVIE_TERROR -> R.layout.list_item_movie_1
            TYPE_MOVIE_DRAMA -> R.layout.list_item_movie_2
            TYPE_HEADER -> R.layout.list_item_movie_header
            else -> throw IllegalArgumentException("Invalid type")
        }
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layout, parent, false)

        return CinemaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CinemaViewHolder, position: Int) {
        holder.onBind(getItem(position), this)
    }

    override fun hideDramas() {
        val dramaList = currentList.map {
            if (it is CinemaModel.DramaMovie) {
                it.copy(showElement = !it.showElement)
            } else it
        }
        submitList(dramaList)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return when (item) {
            is CinemaModel.TerrorMovie -> TYPE_MOVIE_TERROR
            is CinemaModel.DramaMovie -> TYPE_MOVIE_DRAMA
            is CinemaModel.Header -> TYPE_HEADER
        }
    }

    class CinemaDiffCallback : DiffUtil.ItemCallback<CinemaModel>() {
        override fun areItemsTheSame(oldItem: CinemaModel, newItem: CinemaModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CinemaModel, newItem: CinemaModel): Boolean {
            return when (oldItem) {
                is CinemaModel.DramaMovie -> oldItem.showElement == (newItem as CinemaModel.DramaMovie).showElement
                else -> oldItem == newItem
            }
        }
    }
}

interface CinemaClickListeners {
    fun hideDramas()
    // fun hideTerrors()
}
