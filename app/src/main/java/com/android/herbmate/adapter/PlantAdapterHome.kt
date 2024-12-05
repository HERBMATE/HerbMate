package com.android.herbmate.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.herbmate.data.response.TanamanResponseItem
import com.android.herbmate.databinding.ItemPlantHomeBinding
import com.android.herbmate.ui.detail.DetailActivity
import com.bumptech.glide.Glide

class PlantAdapterHome: ListAdapter<TanamanResponseItem, PlantAdapterHome.ListViewHolder>(
    DIFF_CALLBACK
) {
    class ListViewHolder(private val binding: ItemPlantHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(plant: TanamanResponseItem) {
            binding.tvName.text = plant.nama
            val gambar = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d7/Cristiano_Ronaldo_playing_for_Al_Nassr_FC_against_Persepolis%2C_September_2023_%28cropped%29.jpg/330px-Cristiano_Ronaldo_playing_for_Al_Nassr_FC_against_Persepolis%2C_September_2023_%28cropped%29.jpg"
            Glide.with(binding.root.context)
                .load(gambar)
                .into(binding.imgPlant)

            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_NAME, plant.nama)
                    putExtra(DetailActivity.EXTRA_IMAGE, gambar)
                }
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemPlantHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TanamanResponseItem>() {
            override fun areItemsTheSame(oldItem: TanamanResponseItem, newItem: TanamanResponseItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TanamanResponseItem, newItem: TanamanResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
//
// class PlantAdapterHome: ListAdapter<TanamanResponseItem, PlantAdapterHome.ListViewHolder>(
//    DIFF_CALLBACK
//) {
//    class ListViewHolder(private val binding: ItemPlantHomeBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(plant: TanamanResponseItem) {
//            binding.tvName.text = plant.nama
//            val gambar = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d7/Cristiano_Ronaldo_playing_for_Al_Nassr_FC_against_Persepolis%2C_September_2023_%28cropped%29.jpg/330px-Cristiano_Ronaldo_playing_for_Al_Nassr_FC_against_Persepolis%2C_September_2023_%28cropped%29.jpg"
//            Glide.with(binding.root.context)
//                .load(gambar)
//                .into(binding.imgPlant)
//
//            binding.root.setOnClickListener {
//                val intent = Intent(binding.root.context, DetailActivity::class.java).apply {
//                    putExtra(DetailActivity.EXTRA_NAME, plant.nama)
//                    putExtra(DetailActivity.EXTRA_IMAGE, gambar)
//                }
//                binding.root.context.startActivity(intent)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
//        val binding = ItemPlantHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ListViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
//        val event = getItem(position)
//        holder.bind(event)
//    }
//
//    companion object {
//        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TanamanResponseItem>() {
//            override fun areItemsTheSame(oldItem: TanamanResponseItem, newItem: TanamanResponseItem): Boolean {
//                return oldItem.id == newItem.id
//            }
//
//            override fun areContentsTheSame(oldItem: TanamanResponseItem, newItem: TanamanResponseItem): Boolean {
//                return oldItem == newItem
//            }
//        }
//    }
//}
//
//class PlantAdapterHome: ListAdapter<TanamanResponseItem, PlantAdapterHome.ListViewHolder>(
//    DIFF_CALLBACK
//) {
//    class ListViewHolder(private val binding: ItemPlantHomeBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(plant: TanamanResponseItem) {
//            binding.tvName.text = plant.nama
//            val gambar = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d7/Cristiano_Ronaldo_playing_for_Al_Nassr_FC_against_Persepolis%2C_September_2023_%28cropped%29.jpg/330px-Cristiano_Ronaldo_playing_for_Al_Nassr_FC_against_Persepolis%2C_September_2023_%28cropped%29.jpg"
//            Glide.with(binding.root.context)
//                .load(gambar)
//                .into(binding.imgPlant)
//
//            binding.root.setOnClickListener {
//                val intent = Intent(binding.root.context, DetailActivity::class.java).apply {
//                    putExtra(DetailActivity.EXTRA_NAME, plant.nama)
//                    putExtra(DetailActivity.EXTRA_IMAGE, gambar)
//                }
//                binding.root.context.startActivity(intent)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
//        val binding = ItemPlantHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ListViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
//        val event = getItem(position)
//        holder.bind(event)
//    }
//
//    companion object {
//        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TanamanResponseItem>() {
//            override fun areItemsTheSame(oldItem: TanamanResponseItem, newItem: TanamanResponseItem): Boolean {
//                return oldItem.id == newItem.id
//            }
//
//            override fun areContentsTheSame(oldItem: TanamanResponseItem, newItem: TanamanResponseItem): Boolean {
//                return oldItem == newItem
//            }
//        }
//    }
//}