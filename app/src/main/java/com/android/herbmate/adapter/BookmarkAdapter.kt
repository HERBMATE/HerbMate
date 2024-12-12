package com.android.herbmate.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.herbmate.OnBookmarkClickListener
import com.android.herbmate.data.remote.response.BookmarkItem
import com.android.herbmate.databinding.ItemPlantBinding
import com.android.herbmate.ui.detail.DetailActivity
import com.bumptech.glide.Glide

class BookmarkAdapter(private val listener: OnBookmarkClickListener) : ListAdapter<com.android.herbmate.data.remote.response.BookmarkItem, BookmarkAdapter.ListViewHolder>(
    DIFF_CALLBACK
) {
    class ListViewHolder(private val binding: ItemPlantBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(plant: BookmarkItem, listener: OnBookmarkClickListener) {
            binding.tvName.text = plant.nama
            val gambar = plant.gambar
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
                listener.onBookmarkClick(true, 0, idBookmark)
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
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<com.android.herbmate.data.remote.response.BookmarkItem>() {
            override fun areItemsTheSame(oldItem: com.android.herbmate.data.remote.response.BookmarkItem, newItem: com.android.herbmate.data.remote.response.BookmarkItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: com.android.herbmate.data.remote.response.BookmarkItem, newItem: com.android.herbmate.data.remote.response.BookmarkItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}