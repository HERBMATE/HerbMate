package com.android.herbmate.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.herbmate.R
import com.android.herbmate.data.remote.response.TanamanDetailsItem
import com.android.herbmate.databinding.ItemPenyakitBinding

class PenyakitAdapter : ListAdapter<TanamanDetailsItem, PenyakitAdapter.ListViewHolder>(DIFF_CALLBACK) {

    private val expandedPositions = mutableSetOf<Int>()

    class ListViewHolder(private val binding: ItemPenyakitBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TanamanDetailsItem, position: Int, expandedPositions: Set<Int>) {
            binding.tvPenyakit.text = item.penyakit

            binding.llResep.visibility = if (expandedPositions.contains(position)) View.VISIBLE else View.GONE

            if (binding.llResep.childCount == 0 && expandedPositions.contains(position)) {
                val inflater = LayoutInflater.from(binding.llResep.context)
                val viewResep = inflater.inflate(R.layout.item_resep, binding.llResep, false)
                val tvResep = viewResep.findViewById<TextView>(R.id.tv_resep)
                tvResep.text = item.resep
                val tvEfekSamping = viewResep.findViewById<TextView>(R.id.tv_efek_samping)
                tvEfekSamping.text = item.efekSamping
                val tvManfaat = viewResep.findViewById<TextView>(R.id.tv_manfaat)
                tvManfaat.text = item.manfaat
                val tvSumber = viewResep.findViewById<Button>(R.id.tv_sumber)

                // Mengatur klik tombol sumber untuk membuka link
                tvSumber.setOnClickListener {
                    val url = item.sumber
                    if (url.isNotEmpty()) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        binding.llResep.context.startActivity(intent)
                    }
                }

                binding.llResep.addView(viewResep)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemPenyakitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position, expandedPositions)

        holder.itemView.setOnClickListener {
            if (expandedPositions.contains(position)) {
                expandedPositions.remove(position)
            } else {
                expandedPositions.add(position)
            }
            notifyItemChanged(position)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TanamanDetailsItem>() {
            override fun areItemsTheSame(oldItem: TanamanDetailsItem, newItem: TanamanDetailsItem) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: TanamanDetailsItem, newItem: TanamanDetailsItem) = oldItem == newItem
        }
    }
}
