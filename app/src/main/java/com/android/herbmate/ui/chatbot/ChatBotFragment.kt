package com.android.herbmate.ui.chatbot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.herbmate.ChatBotAdapter
import com.android.herbmate.R
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import com.android.herbmate.ChatMessage

class ChatBotFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var chatBotAdapter: ChatBotAdapter
    private val messages = mutableListOf<ChatMessage>()

    private val faqList = listOf(
        "Apa itu chatbot?",
        "Bagaimana cara kerja chatbot?",
        "Apa manfaat menggunakan chatbot?"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        chatBotAdapter = ChatBotAdapter(messages)
        recyclerView.adapter = chatBotAdapter

        // Menampilkan FAQ
        showFAQ()

        // Menangani klik pada pertanyaan FAQ
        view.findViewById<Button>(R.id.btn_send).setOnClickListener {
            // Kirim pesan dari pengguna
            val userMessage = "Pertanyaan dari pengguna"
            addMessage(userMessage, true)
            // Simulasi respons dari chatbot
            addMessage("Ini adalah respons dari chatbot.", false)
        }
    }

    private fun showFAQ() {
        for (faq in faqList) {
            addMessage(faq, false) // Menampilkan FAQ sebagai pesan dari chatbot
        }
    }

    private fun addMessage(message: String, isUser : Boolean) {
        messages.add(ChatMessage(message, isUser ))
        chatBotAdapter.notifyItemInserted(messages.size - 1)
        recyclerView.scrollToPosition(messages.size - 1)
    }
}