package com.android.herbmate.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.herbmate.data.local.entity.HistoryEntity
import com.android.herbmate.R
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Locale

class HistoryAdapter(private var historyList: List<HistoryEntity>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageThumbnail: ImageView = view.findViewById(R.id.imageThumbnail)
        val timestamp: TextView = view.findViewById(R.id.timestamp)
        val plantName: TextView = view.findViewById(R.id.plant)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = historyList[position]
        val formatedDate = formatTimestamp(history.timestamp)
        Glide.with(holder.itemView.context).load(history.filePath).into(holder.imageThumbnail)
        holder.timestamp.text = formatedDate
        holder.plantName.text = history.plant
    }

    private fun formatTimestamp(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy HH:mm:ss", Locale.getDefault())
        return dateFormat.format(timestamp)
    }

    override fun getItemCount() = historyList.size

    // Method untuk memperbarui data
    fun updateData(newHistoryList: List<HistoryEntity>) {
        historyList = newHistoryList
        notifyDataSetChanged()
    }
}
