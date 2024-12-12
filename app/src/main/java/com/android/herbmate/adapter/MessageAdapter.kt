package com.android.herbmate.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.herbmate.data.model.Message
import com.android.herbmate.R
import de.hdodenhof.circleimageview.CircleImageView

class MessageAdapter(private val messages: MutableList<Message>, private var userName: String) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewMessage: TextView = itemView.findViewById(R.id.textViewMessage)
        val imageViewProfile: CircleImageView = itemView.findViewById(R.id.imageViewProfile)
        val messageLayout: LinearLayout = itemView.findViewById(R.id.messageLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]

        holder.textViewMessage.text = Html.fromHtml(message.content, Html.FROM_HTML_MODE_LEGACY)

        if (message.isSentByUser ) {
            holder.textViewName.text = userName
            holder.imageViewProfile.visibility = View.GONE
            holder.textViewMessage.setBackgroundResource(R.drawable.bg_message_sent)
        } else {
            holder.textViewName.text = "HerbMate"
            holder.imageViewProfile.setImageResource(R.drawable.ic_herbmate)
            holder.imageViewProfile.visibility = View.VISIBLE
            holder.textViewMessage.setBackgroundResource(R.drawable.bg_message_received)

        }
    }

    override fun getItemCount(): Int = messages.size

    fun updateMessages(newMessages: List<Message>) {
        messages.clear()
        messages.addAll(newMessages)
        notifyDataSetChanged()
    }

    fun setUserName(name: String) {
        userName = name
        notifyDataSetChanged()
    }
}

