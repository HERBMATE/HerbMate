package com.android.herbmate.adapter

// MessageAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.herbmate.Message
import com.android.herbmate.R

class MessageAdapter(private val messages: MutableList<Message>) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textViewMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.textView.text = message.content

        // Atur latar belakang berdasarkan pengirim
        holder.textView.setBackgroundResource(
            if (message.isSentByUser) R.drawable.bg_message_sent
            else R.drawable.bg_message_received
        )

        // Atur posisi berdasarkan pengirim
        val params = holder.textView.layoutParams as LinearLayout.LayoutParams

        if (message.isSentByUser) {
            // Pesan dari pengguna: geser ke kanan
            params.gravity = android.view.Gravity.END
        } else {
            // Pesan dari lawan bicara: geser ke kiri
            params.gravity = android.view.Gravity.START
        }

        holder.textView.layoutParams = params
    }

    override fun getItemCount(): Int = messages.size

    fun updateMessages(newMessages: List<Message>) {
        messages.clear()
        messages.addAll(newMessages)
        notifyDataSetChanged()
    }
}
