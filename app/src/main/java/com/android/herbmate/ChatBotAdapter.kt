package com.android.herbmate

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.herbmate.data.pref.UserPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatBotAdapter(private val messages: List<Message>) : RecyclerView.Adapter<ChatBotAdapter.MessageViewHolder>() {

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(android.R.id.text1)
//        private val profileImageView: ImageView = itemView.findViewById(R.id.imageViewProfile)
//        private val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        fun bind(message: Message) {
            textView.text = message.content
            if (message.isSentByUser) {
//                textViewName.text = "Test"
//                profileImageView.visibility = View.GONE
                textView.setBackgroundColor(Color.BLUE)
                textView.setTextColor(Color.WHITE)
            } else {
                textView.setBackgroundColor(Color.GRAY)
                textView.setTextColor(Color.BLACK)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.bind(message)
    }

    override fun getItemCount(): Int {
        return messages.size
    }
}