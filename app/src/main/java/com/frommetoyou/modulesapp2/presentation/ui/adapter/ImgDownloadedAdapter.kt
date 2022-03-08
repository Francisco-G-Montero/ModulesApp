package com.frommetoyou.modulesapp2.presentation.ui.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.frommetoyou.modulesapp2.databinding.ListItemImagesBinding

class ImgDownloadedAdapter() : ListAdapter<String, ImgDownloadedAdapter.LeafViewHolder>(LeafDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeafViewHolder {
        val binding = ListItemImagesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LeafViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LeafViewHolder, position: Int) {
        holder.onBind(currentList[holder.adapterPosition])
    }

    class LeafViewHolder(private val binding: ListItemImagesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(imgPath: String) {
            with(binding) {
                val bitmap = BitmapFactory.decodeFile(imgPath)
                Glide.with(root)
                    .load(bitmap)
                    .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(15)))
                    .into(imgDownloaded)
            }
        }
    }

    class LeafDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}
