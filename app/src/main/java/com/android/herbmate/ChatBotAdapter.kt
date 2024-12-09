package com.android.herbmate

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//class ChatBotAdapter(private val messages: List<ChatMessage>) : RecyclerView.Adapter<ChatBotAdapter.MessageViewHolder>() {
//
//    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val textView: TextView = itemView.findViewById(android.R.id.text1)
//
//        fun bind(message: ChatMessage) {
//            textView.text = message.message
//            // Optionally, you can change the appearance based on isUser
//            if (message.isUser ) {
//                // Style for user messages
//                textView.setBackgroundColor(Color.BLUE) // Example styling
//                textView.setTextColor(Color.WHITE)
//            } else {
//                // Style for bot messages
//                textView.setBackgroundColor(Color.GRAY) // Example styling
//                textView.setTextColor(Color.BLACK)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
//        return MessageViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
//        val message = messages[position]
//        holder.bind(message)
//    }
//
//    override fun getItemCount(): Int {
//        return messages.size
//    }
//}