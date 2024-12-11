package com.android.herbmate.ui.chatbot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.herbmate.Message
import com.android.herbmate.R
import com.android.herbmate.ViewModelFactory
import com.android.herbmate.adapter.MessageAdapter

class ChatBotFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var editTextMessage: EditText
    private lateinit var buttonSend: Button
    private lateinit var messageAdapter: MessageAdapter

    private val viewModel by viewModels<ChatBotViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_chat_bot, container, false)

//        recyclerView = view.findViewById(R.id.recyclerView)
//        editTextMessage = view.findViewById(R.id.editTextMessage)
//        buttonSend = view.findViewById(R.id.buttonSend)
//
//        messageAdapter = MessageAdapter(mutableListOf())
//        recyclerView.adapter = messageAdapter
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//
//        observeViewModel()
//
//        buttonSend.setOnClickListener {
//            val messageContent = editTextMessage.text.toString()
//            if (messageContent.isNotEmpty()) {
//                viewModel.sendMessage(messageContent)
//                editTextMessage.text.clear()
//            }
//        }

        return view
    }

//    private fun observeViewModel() {
//        viewModel.messages.observe(viewLifecycleOwner, Observer { messages ->
//            messageAdapter.updateMessages(messages)
//            recyclerView.scrollToPosition(messages.size - 1)
//        })
//    }
}
