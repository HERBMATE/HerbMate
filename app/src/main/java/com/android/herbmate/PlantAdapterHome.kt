package com.android.herbmate

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.herbmate.ui.detail.DetailActivity
import com.bumptech.glide.Glide

class PlantAdapterHome(private val context: Context, private val plants: List<Plant>) : RecyclerView.Adapter<PlantAdapterHome.ListViewHolder>() {
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image : ImageView = itemView.findViewById(R.id.img_plant)
        val name: TextView = itemView.findViewById(R.id.tv_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_plant_home, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = plants.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val plant = plants[position]

        holder.name.text = plant.name
        Glide.with(holder.itemView.context)
            .load(plant.image)
            .into(holder.image)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_NAME, plant.name)
                putExtra(DetailActivity.EXTRA_IMAGE, plant.image)
            }
            context.startActivity(intent)
        }

    }
}