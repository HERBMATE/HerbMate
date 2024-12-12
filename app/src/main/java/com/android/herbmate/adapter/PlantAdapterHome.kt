package com.android.herbmate.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.herbmate.OnBookmarkClickListener
import com.android.herbmate.R
import com.android.herbmate.data.remote.response.TanamanItem
import com.android.herbmate.databinding.ItemPlantHomeBinding
import com.android.herbmate.ui.detail.DetailActivity
import com.bumptech.glide.Glide

class PlantAdapterHome(private val listener: OnBookmarkClickListener) : ListAdapter<TanamanItem, PlantAdapterHome.ListViewHolder>(
    DIFF_CALLBACK
) {
    class ListViewHolder(private val binding: ItemPlantHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(plant: TanamanItem, listener: OnBookmarkClickListener) {
            binding.tvName.text = plant.nama
            val gambar = plant.gambar
            Glide.with(binding.root.context)
                .load(gambar)
                .into(binding.imgPlant)

            updateBookmarkIcon(plant.status)

            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_NAME, plant.nama)
                    putExtra(DetailActivity.EXTRA_IMAGE, gambar)
                    putExtra(DetailActivity.EXTRA_ASAL, plant.asal)
                    putExtra(DetailActivity.EXTRA_LATIN, plant.namaLatin)
                    putExtra(DetailActivity.EXTRA_KANDUNGAN, plant.kandungan)
                    putExtra(DetailActivity.EXTRA_STATUS, plant.status)
                    putExtra(DetailActivity.EXTRA_ID_BOOKMARK, plant.idBookmark)
                }
                binding.root.context.startActivity(intent)
            }

            binding.btnBookmark.setOnClickListener {
                val currentStatus = plant.status
                listener.onBookmarkClick(currentStatus, plant.id, plant.idBookmark)
                plant.status = !currentStatus
                updateBookmarkIcon(plant.status)
            }
        }

        private fun updateBookmarkIcon(isBookmarked: Boolean) {
            if (isBookmarked) {
                binding.btnBookmark.setImageResource(R.drawable.ic_bookmark)
            } else {
                binding.btnBookmark.setImageResource(R.drawable.ic_bookmark_border)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemPlantHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val plant = getItem(position)
        holder.bind(plant, listener)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TanamanItem>() {
            override fun areItemsTheSame(oldItem: TanamanItem, newItem: TanamanItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TanamanItem, newItem: TanamanItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}