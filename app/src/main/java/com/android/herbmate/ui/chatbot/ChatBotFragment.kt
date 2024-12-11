package com.android.herbmate.ui.chatbot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.herbmate.data.ViewModelFactory
import com.android.herbmate.databinding.FragmentChatBotBinding
import com.android.herbmate.adapter.MessageAdapter

class ChatBotFragment : Fragment() {

    private var _binding: FragmentChatBotBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChatBotViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private lateinit var messageAdapter: MessageAdapter
    private var userName:String = "User"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        messageAdapter = MessageAdapter(mutableListOf(), userName)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = messageAdapter
        }

        // Observasi data di ViewModel
        viewModel.messages.observe(viewLifecycleOwner) { messages ->
            messageAdapter.updateMessages(messages)
            binding.recyclerView.scrollToPosition(messages.size - 1)
        }

        // Tombol kirim pesan
        binding.buttonSend.setOnClickListener {
            val messageContent = binding.editTextMessage.text.toString()
            if (messageContent.isNotEmpty()) {
                viewModel.sendMessage(messageContent)
                binding.editTextMessage.text?.clear()
            }
        }

        viewModel.userSession.observe(viewLifecycleOwner){ result ->
            if (result != null) {
                userName = result.email
                messageAdapter.setUserName(userName)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
