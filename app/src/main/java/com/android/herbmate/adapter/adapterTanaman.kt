//package com.android.herbmate.adapter
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.android.herbmate.R
//
//class TanamanAdapter : ListAdapter<String?, TanamanAdapter.TanamanViewHolder>(DIFF_CALLBACK) {
//
//    // ViewHolder class to hold the views for each item
//    class TanamanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val tanamanTextView: TextView = itemView.findViewById(R.id.tv_tanaman) // Assuming you have a TextView with this ID
//    }
//
//    // Create new views (invoked by the layout manager)
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TanamanViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_faq, parent, false) // Inflate your item layout
//        return TanamanViewHolder(view)
//    }
//
//    // Replace the contents of a view (invoked by the layout manager)
//    override fun onBindViewHolder(holder: TanamanViewHolder, position: Int) {
//        val tanaman = getItem(position)
//        holder.tanamanTextView.text = tanaman ?: "Unknown" // Handle null values
//    }
//
//    // DiffUtil callback to determine changes in the list
//    companion object {
//        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String?>() {
//            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
//                return oldItem == newItem // Compare items based on their content
//            }
//
//            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
//                return oldItem == newItem // Compare contents
//            }
//        }
//    }
//}