package com.android.herbmate

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.herbmate.data.response.BookmarkItem
import com.android.herbmate.data.response.TanamanItem
import com.android.herbmate.databinding.ItemPlantBinding
import com.android.herbmate.ui.detail.DetailActivity
import com.bumptech.glide.Glide

class PlantAdapter(private val listener: OnBookmarkClickListener) : ListAdapter<BookmarkItem, PlantAdapter.ListViewHolder>(DIFF_CALLBACK) {
    class ListViewHolder(private val binding: ItemPlantBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(plant: BookmarkItem, listener: OnBookmarkClickListener) {
            binding.tvName.text = plant.nama
            val gambar = "https://storage.googleapis.com/test-ren-bucket/Lidah%20Buaya.jpg"
            Glide.with(binding.root.context)
                .load(gambar)
                .into(binding.imgPlant)

            val idBookmark = plant.idBookmark
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_NAME, plant.nama)
                    putExtra(DetailActivity.EXTRA_IMAGE, gambar)
                }
                binding.root.context.startActivity(intent)
            }

            binding.btnBookmark.setOnClickListener {
                listener.onBookmarkClick(idBookmark) // Call the listener with only idTanaman
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemPlantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val plant = getItem(position)
        holder.bind(plant, listener)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BookmarkItem>() {
            override fun areItemsTheSame(oldItem: BookmarkItem, newItem: BookmarkItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: BookmarkItem, newItem: BookmarkItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}